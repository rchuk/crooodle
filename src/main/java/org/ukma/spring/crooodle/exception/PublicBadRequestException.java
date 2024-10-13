package org.ukma.spring.crooodle.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PublicBadRequestException extends PublicAppException {
    public PublicBadRequestException(String message) {
        super(message);
    }
}
