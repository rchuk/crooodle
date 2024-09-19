package org.ukma.spring.crooodle.repository;

import org.ukma.spring.crooodle.model.User;

public interface UserRepository {
    User getUserByEmail(String email);
}
