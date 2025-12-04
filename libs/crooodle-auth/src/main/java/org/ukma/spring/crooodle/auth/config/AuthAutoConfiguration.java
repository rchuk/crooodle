package org.ukma.spring.crooodle.auth.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.ukma.spring.crooodle.auth.GrpcAuthenticationFilter;
import org.ukma.spring.crooodle.auth.SecurityUtils;

@AutoConfiguration
@Import(SecurityConfig.class)
public class AuthAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public GrpcAuthenticationFilter grpcAuthenticationFilter() {
		return new GrpcAuthenticationFilter();
	}

	@Bean
	@ConditionalOnMissingBean
	public SecurityUtils securityUtils() {
		return new SecurityUtils();
	}
}
