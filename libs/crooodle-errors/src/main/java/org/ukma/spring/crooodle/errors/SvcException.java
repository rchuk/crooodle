package org.ukma.spring.crooodle.errors;

import lombok.Getter;

@Getter
public class SvcException extends RuntimeException {
	private final SvcError serviceError;

	public SvcException(SvcError serviceError) {
		super(serviceError.getDefaultMessage());
		this.serviceError = serviceError;
	}

	public SvcException(SvcError serviceError, String customMessage) {
		super(customMessage);
		this.serviceError = serviceError;
	}
}
