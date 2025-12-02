package org.ukma.spring.crooodle.errors;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@GrpcAdvice
public class ErrorGrpcAdvice {
	@GrpcExceptionHandler(SvcException.class)
	public StatusRuntimeException handleSvcException(SvcException e) {
		log.debug("Error: {}", e.getMessage());

		var status = e.getServiceError().getGrpcStatus();

		return status.withDescription(e.getMessage())
			.withCause(e)
			.asRuntimeException();
	}

	@GrpcExceptionHandler(ConstraintViolationException.class)
	public StatusRuntimeException handleValidationException(ConstraintViolationException e) {
		var errorMessage = e.getConstraintViolations().stream()
			.map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
			.collect(Collectors.joining(", "));

		log.debug("Validation failed: {}", errorMessage);

		return SvcError.INVALID_REQUEST.getGrpcStatus()
			.withDescription("Validation failed: " + errorMessage)
			.asRuntimeException();
	}

	@GrpcExceptionHandler(Exception.class)
	public StatusRuntimeException handleGenericException(Exception e) {
		log.error("Unexpected Internal Error: ", e);

		return Status.INTERNAL
			.withDescription("An unexpected internal error occurred")
			.withCause(e)
			.asRuntimeException();
	}
}
