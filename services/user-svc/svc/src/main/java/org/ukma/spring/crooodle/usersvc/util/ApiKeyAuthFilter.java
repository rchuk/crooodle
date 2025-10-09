package org.ukma.spring.crooodle.usersvc.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;

public class ApiKeyAuthFilter extends OncePerRequestFilter {
	private final byte[] expectedKey;

	public ApiKeyAuthFilter(String apiKey) {
		this.expectedKey = apiKey == null ? new byte[0] : apiKey.getBytes(java.nio.charset.StandardCharsets.UTF_8);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
		throws ServletException, IOException {

		String auth = req.getHeader("Authorization");
		if (auth == null || !auth.startsWith("Bearer ")) {
			String key = req.getHeader("X-API-Key");
			if (key != null && !key.isBlank() && constantTimeEquals(expectedKey, key)) {
				var authn = new UsernamePasswordAuthenticationToken(
					"internal-service", null, List.of(new SimpleGrantedAuthority("ROLE_SERVICE")));
				SecurityContextHolder.getContext().setAuthentication(authn);
			}
		}
		chain.doFilter(req, res);
	}

	private static boolean constantTimeEquals(byte[] expected, String provided) {
		byte[] p = provided.getBytes(java.nio.charset.StandardCharsets.UTF_8);
		return MessageDigest.isEqual(expected, p);
	}
}
