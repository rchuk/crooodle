package org.ukma.spring.crooodle.usersvc.support;

public class EntityNotFoundException extends RuntimeException {
	public EntityNotFoundException(Object id, String entity) {
		super(entity + " not found: " + id);
	}
}
