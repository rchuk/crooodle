package org.ukma.spring.crooodle.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.ukma.spring.crooodle.entities.UserEntity;

public interface UserService extends UserDetailsService {
    UserEntity getCurrentUser();
}
