package org.ukma.spring.crooodle.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.ukma.spring.crooodle.model.User;

public interface UserService extends UserDetailsService {
    User getCurrentUser();
}
