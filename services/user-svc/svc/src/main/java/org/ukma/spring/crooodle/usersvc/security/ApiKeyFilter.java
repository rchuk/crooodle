package org.ukma.spring.crooodle.usersvc.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {
	@Value("${app.internal.api-key}") private String expected;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest r = (HttpServletRequest) req;
		HttpServletResponse w = (HttpServletResponse) res;

		if (r.getRequestURI().startsWith("/internal/")) {
			String key = r.getHeader("X-API-KEY");
			if (key == null || !key.equals(expected)) {
				w.setStatus(HttpStatus.UNAUTHORIZED.value());
				return;
			}
		}
		chain.doFilter(req, res);
	}
}
