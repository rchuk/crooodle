package org.ukma.spring.crooodle.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PublicServerException extends PublicAppException {
    public PublicServerException(String message) {
        super(message);
    }
}
