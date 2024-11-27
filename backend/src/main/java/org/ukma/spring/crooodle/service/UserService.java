package org.ukma.spring.crooodle.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.ukma.spring.crooodle.entities.UserEntity;
import org.ukma.spring.crooodle.entities.enums.UserPermission;

import java.util.Set;

public interface UserService extends UserDetailsService {
    UserEntity getCurrentUser();
    void addPermissions(String email, Set<UserPermission> permission);
}
