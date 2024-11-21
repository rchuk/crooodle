package org.ukma.spring.crooodle.dto.common;

import lombok.Builder;
import lombok.Value;

/**
 * DTO for sending error messages that might be shown to user as json.
 * Used for all exceptions that application might throw
 *
 * @see org.ukma.spring.crooodle.app.ControllerExceptionHandler
 */
@Builder
@Value
public class PublicErrorDto {
    String message;

    public static PublicErrorDto fromMessage(String message) {
        return PublicErrorDto.builder().message(message).build();
    }
}
