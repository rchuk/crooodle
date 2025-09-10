package org.ukma.spring.crooodle.utils.exceptions.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;
import org.ukma.spring.crooodle.utils.exceptions.ForbiddenException;
import org.ukma.spring.crooodle.utils.exceptions.PublicException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class CrooodleExceptionHandler {
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionDto> handleForbiddenException(EntityNotFoundException e) {
        return new ResponseEntity<>(ExceptionDto.builder().message(e.getMessage()).build(), getHttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(ExceptionDto.builder().message(e.getMessage()).build(), getHttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PublicException.class)
    public ResponseEntity<ExceptionDto> handlePublicException(PublicException e) {
        return new ResponseEntity<>(ExceptionDto.builder().message(e.getMessage()).build(), getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(ExceptionDto.builder().message(e.getMessage()).build(), getHttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors()
            .stream()
            .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
        var dto = ValidationExceptionDto.builder()
            .message("Validation error")
            .errors(errors)
            .build();

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handleUnexpectedException(Exception e) {
        return new ResponseEntity<>(ExceptionDto.builder().message("Unexpected error").build(), getHttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static HttpHeaders getHttpHeaders() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }
}
