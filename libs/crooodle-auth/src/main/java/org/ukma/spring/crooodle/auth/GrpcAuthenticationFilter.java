package org.ukma.spring.crooodle.auth;

import io.grpc.StatusRuntimeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ukma.spring.crooodle.authsvc.grpc.*;

import java.io.IOException;
import java.util.List;

@Component
public class GrpcAuthenticationFilter extends OncePerRequestFilter {
	@GrpcClient("auth-svc")
	private AuthServiceGrpc.AuthServiceBlockingStub authStub;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
																	@NonNull HttpServletResponse response,
																	@NonNull FilterChain chain) throws ServletException, IOException {
		var token = resolveToken(request);

		if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				var requestDto = AccessToken.newBuilder()
					.setToken(token)
					.build();

				var grpcUser = authStub.getUser(requestDto);
				var roleName = "ROLE_" + grpcUser.getRole().name();
				var authorities = List.of(new SimpleGrantedAuthority(roleName));

				var principal = new AuthenticatedUser(
					grpcUser.getId(),
					grpcUser.getUsername(),
					authorities
				);

				var authentication = new UsernamePasswordAuthenticationToken(
					principal,
					null,
					authorities
				);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);

			} catch (StatusRuntimeException e) {
				logger.warn("gRPC Authentication failed: " + e.getStatus());
			}
		}

		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
