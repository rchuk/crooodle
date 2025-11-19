package org.ukma.spring.crooodle.hotelsvc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {
	@Value("${security.internal.api-key}")
	String apiKey;

	@Bean
	feign.RequestInterceptor authInterceptor() {
		return template -> {
			String url = template.url();
			if (url.startsWith("/internal/")) {
				template.header("X-API-Key", apiKey);
				template.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			} else {
				var attrs = RequestContextHolder.getRequestAttributes();
				if (attrs instanceof ServletRequestAttributes sra) {
					String auth = sra.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
					if (auth != null && !auth.isBlank()) {
						template.header(HttpHeaders.AUTHORIZATION, auth);
					}
				}
			}
		};
	}

}
