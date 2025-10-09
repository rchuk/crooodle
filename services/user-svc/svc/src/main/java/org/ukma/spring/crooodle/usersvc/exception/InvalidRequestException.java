package org.ukma.spring.crooodle.usersvc.exception;

public class InvalidRequestException extends PublicException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
