package org.ukma.spring.crooodle.repository.impl;

import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User getUserByEmail(String email) {
        return null;
    }
}
