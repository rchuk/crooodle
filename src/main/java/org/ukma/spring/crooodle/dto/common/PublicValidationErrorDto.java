package org.ukma.spring.crooodle.dto.common;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

/**
 * DTO for sending error messages related to validation that might be shown to user as json.
 * Used for {@link org.springframework.web.bind.MethodArgumentNotValidException} exceptions.
 *
 * @see org.ukma.spring.crooodle.app.ControllerExceptionHandler
 */
@Builder
@Value
public class PublicValidationErrorDto {
    String message;
    Map<String, String> errors;
}
