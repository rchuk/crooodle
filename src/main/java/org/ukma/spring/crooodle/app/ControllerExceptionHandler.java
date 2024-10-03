package org.ukma.spring.crooodle.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
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
    public ResponseEntity<PublicErrorDto> handlePublicNotFoundException(PublicNotFoundException ex, WebRequest request) {
        var errorDto = PublicErrorDto.builder().message(ex.getMessage()).build();

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PublicBadRequestException.class)
    public ResponseEntity<PublicErrorDto> handlePublicBadRequestException(PublicBadRequestException ex, WebRequest request) {
        var errorDto = PublicErrorDto.builder().message(ex.getMessage()).build();

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<PublicValidationErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        e -> Optional.ofNullable(e.getDefaultMessage()).orElseGet(String::new))
                );
        var errorDto = PublicValidationErrorDto.builder()
                .message("Validation error")
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PublicServerException.class)
    public ResponseEntity<PublicErrorDto> handlePublicServerException(PublicBadRequestException ex, WebRequest request) {
        var errorDto = PublicErrorDto.builder().message(ex.getMessage()).build();

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<PublicErrorDto> handleUnknownException(Exception ex) {
        log.debug("Unhandled exception: %s", ex);
        var errorDto = PublicErrorDto.builder().message("Unknown error").build();

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
