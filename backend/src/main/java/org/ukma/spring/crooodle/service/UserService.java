package org.ukma.spring.crooodle.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.ukma.spring.crooodle.entities.UserEntity;
import org.ukma.spring.crooodle.entities.enums.UserPermission;

public interface UserService extends UserDetailsService {
    UserEntity getCurrentUser();
    void addPermission(String email, UserPermission permission);
}
