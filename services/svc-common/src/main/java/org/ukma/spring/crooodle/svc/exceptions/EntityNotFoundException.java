package org.ukma.spring.crooodle.svc.exceptions;

public class EntityNotFoundException extends CrooodleException {
	public EntityNotFoundException(Object id, String message) {
		super(String.format("%s with ID '%s' not found", message, id));
	}
}
