package org.ukma.spring.crooodle.repository.impl;

import org.springframework.stereotype.Repository;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User getUserByEmail(String email) {
        return null;
    }
}
