package org.ukma.spring.crooodle.errors;

import io.grpc.Status;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SvcError {
	INTERNAL_ERROR("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR, Status.INTERNAL),
	INVALID_REQUEST("Invalid request parameters", HttpStatus.BAD_REQUEST, Status.INVALID_ARGUMENT),
	UNAUTHORIZED("Authentication required", HttpStatus.UNAUTHORIZED, Status.UNAUTHENTICATED),
	ACCESS_DENIED("Insufficient permissions", HttpStatus.FORBIDDEN, Status.PERMISSION_DENIED),
	NOT_FOUND("Entity not found", HttpStatus.NOT_FOUND, Status.NOT_FOUND);

	private final String defaultMessage;
	private final HttpStatus httpStatus;
	private final Status grpcStatus;

	SvcError(String defaultMessage, HttpStatus httpStatus, Status grpcStatus) {
		this.defaultMessage = defaultMessage;
		this.httpStatus = httpStatus;
		this.grpcStatus = grpcStatus;
	}
}
