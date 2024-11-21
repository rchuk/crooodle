package org.ukma.spring.crooodle.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PublicNotFoundException extends PublicAppException {
    public PublicNotFoundException(String message) {
        super(message);
    }
}
