package org.ukma.spring.crooodle.hotelsvc.utils;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignAuthRelay {
	@Bean
	public RequestInterceptor authRelayInterceptor() {
		return template -> {
			var attrs = RequestContextHolder.getRequestAttributes();
			if (attrs instanceof ServletRequestAttributes sra) {
				String auth = sra.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
				if (auth != null && !auth.isBlank()) {
					template.header(HttpHeaders.AUTHORIZATION, auth);
				}
			}
		};
	}
}
