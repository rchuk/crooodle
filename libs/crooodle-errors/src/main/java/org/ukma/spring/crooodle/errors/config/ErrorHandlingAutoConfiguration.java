package org.ukma.spring.crooodle.errors.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.ukma.spring.crooodle.errors.ErrorControllerAdvice;
import org.ukma.spring.crooodle.errors.ErrorGrpcAdvice;

@AutoConfiguration
@ConditionalOnClass(ControllerAdvice.class)
public class ErrorHandlingAutoConfiguration {
	@Bean
	@ConditionalOnMissingBean
	public ErrorControllerAdvice errorControllerAdvice() {
		return new ErrorControllerAdvice();
	}

	@Bean
	@ConditionalOnMissingBean
	public ErrorGrpcAdvice errorGrpcAdvice() {
		return new ErrorGrpcAdvice();
	}
}
