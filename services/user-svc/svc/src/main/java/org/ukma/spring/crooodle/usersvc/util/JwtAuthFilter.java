package org.ukma.spring.crooodle.usersvc.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.ukma.spring.crooodle.usersvc.repository.UserRepo;
import org.ukma.spring.crooodle.usersvc.service.JwtService;

@RequiredArgsConstructor
public class JwtAuthFilter extends org.springframework.web.filter.OncePerRequestFilter {
	private final JwtService jwt;
	private final UserRepo users;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
		throws ServletException, IOException {
		String auth = req.getHeader("Authorization");
		if (auth != null && auth.startsWith("Bearer ")) {
			String token = auth.substring(7);
			try {
				var claims = jwt.parser().build().parseSignedClaims(token).getPayload();
				String email = claims.getSubject();
				var user = users.findByEmail(email).orElse(null);
				if (user != null) {
					var authn = new UsernamePasswordAuthenticationToken(
						user, null, user.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authn);
				}
			} catch (Exception ignored) {}
		}
		chain.doFilter(req, res);
	}
}
