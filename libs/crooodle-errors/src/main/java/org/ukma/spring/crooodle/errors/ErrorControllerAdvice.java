package org.ukma.spring.crooodle.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorControllerAdvice {
	@ExceptionHandler(SvcException.class)
	public ResponseEntity<ErrorResponseDto> handleAppException(SvcException e) {
		log.debug("Error: {}", e.getMessage());

		var serviceError = e.getServiceError();
		var response = new ErrorResponseDto(e.getMessage());

		return new ResponseEntity<>(response, serviceError.getHttpStatus());
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AuthorizationDeniedException e) {
		log.debug("Error: {}", e.getMessage());

		var response = new ErrorResponseDto("Access denied");

		return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
		var errorMessage = e.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
			.collect(Collectors.joining(", "));

		log.debug("Validation failed: {}", errorMessage);

		return new ResponseEntity<>(
			new ErrorResponseDto("Validation failed: " + errorMessage),
			SvcError.INVALID_REQUEST.getHttpStatus()
		);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
		log.error("Unexpected Internal Error: ", e);

		var response = new ErrorResponseDto("An unexpected internal error occurred.");

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
