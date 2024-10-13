package org.ukma.spring.crooodle.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class PublicAppException extends RuntimeException {
    public PublicAppException(String message) {
        super(message);
    }
}
