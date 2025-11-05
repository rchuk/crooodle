package org.ukma.spring.crooodle.reservationsvc.exception;

public class InvalidRequestException extends PublicException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
