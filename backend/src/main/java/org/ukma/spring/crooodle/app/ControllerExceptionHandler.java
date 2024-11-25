package org.ukma.spring.crooodle.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.ukma.spring.crooodle.dto.common.PublicErrorDto;
import org.ukma.spring.crooodle.dto.common.PublicValidationErrorDto;
import org.ukma.spring.crooodle.exception.PublicBadRequestException;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.exception.PublicServerException;

import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(PublicNotFoundException.class)
    public ResponseEntity<PublicErrorDto> handlePublicNotFoundException(PublicNotFoundException ex) {
        log.info("PublicNotFoundException handled for: {}", ex.getMessage());
        return new ResponseEntity<>(
            PublicErrorDto.fromMessage(ex.getMessage()),
            HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PublicBadRequestException.class)
    public ResponseEntity<PublicErrorDto> handlePublicBadRequestException(PublicBadRequestException ex) {
        return new ResponseEntity<>(
            PublicErrorDto.fromMessage(ex.getMessage()),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PublicValidationErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                e -> Optional.ofNullable(e.getDefaultMessage()).orElseGet(String::new)
            ));
        var errorDto = PublicValidationErrorDto.builder()
            .message("Validation error")
            .errors(errors)
            .build();

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PublicServerException.class)
    public ResponseEntity<PublicErrorDto> handlePublicServerException(PublicBadRequestException ex) {
        return new ResponseEntity<>(
            PublicErrorDto.fromMessage(ex.getMessage()),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<PublicErrorDto> handleAccessDeniedException(AccessDeniedException ignoredEx) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<PublicErrorDto> handleNoResourceFoundException(NoResourceFoundException ignoredEx) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<PublicErrorDto> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException ignoredEx) {
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<PublicErrorDto> handleMessageNotReadableException(HttpMessageNotReadableException ignoredEx) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PublicErrorDto> handleUnknownException(Exception ex) {

        log.error("Unhandled exception: {}", ex.getMessage(), ex);

        return new ResponseEntity<>(
            PublicErrorDto.fromMessage(ex.getMessage() != null ? ex.getMessage() : "Unknown error"),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
