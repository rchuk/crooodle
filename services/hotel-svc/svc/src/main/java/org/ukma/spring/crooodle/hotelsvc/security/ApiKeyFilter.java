package org.ukma.spring.crooodle.hotelsvc.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

	@Value("${app.internal.api-key}")
	private String expected;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
		throws IOException, jakarta.servlet.ServletException {

		String path = req.getRequestURI();

		if (path.startsWith("/internal/")) {
			String provided = req.getHeader("X-API-KEY");
			if (provided == null || !provided.equals(expected)) {
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			var auth = new UsernamePasswordAuthenticationToken(
				"internal-service", null, List.of(new SimpleGrantedAuthority("ROLE_INTERNAL")));
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		chain.doFilter(req, res);
	}
}
