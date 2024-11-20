package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.entities.UserEntity;
import org.ukma.spring.crooodle.entities.enums.UserPermission;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.repository.UserPermissionRepository;
import org.ukma.spring.crooodle.repository.UserRepository;
import org.ukma.spring.crooodle.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserPermissionRepository permissionRepository;

    @Override
    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var email = auth.getName();

        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public void addPermission(String email, UserPermission permission) {
        var user = userRepository.findByEmail(email).orElseThrow(PublicNotFoundException::new);
        var permissionEntity = permissionRepository.findByKind(permission).orElseThrow();

        user.getPermissions().add(permissionEntity);
        userRepository.saveAndFlush(user);
    }
}
