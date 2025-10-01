package org.ukma.spring.crooodle.usersvc.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ukma.spring.crooodle.usersvc.internal.RoleRepo;
import org.ukma.spring.crooodle.usersvc.internal.UserEntity;
import org.ukma.spring.crooodle.usersvc.internal.UserRepo;
import org.ukma.spring.crooodle.usersvc.support.EntityNotFoundException;
import org.ukma.spring.crooodle.usersvc.support.InvalidRequestException;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;
import org.ukma.spring.crooodle.usersvc.dto.UserRegisterDto;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.dto.RegisterRole;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServic implements UserDetailsService {
    private final UserRepo repo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Transactional
    public UserResponseDto register(UserRegisterDto dto) {
        if (repo.existsByEmail(dto.email()))
            throw new InvalidRequestException("User with the following email already exists");

        var role = switch (dto.role()) {
            case TRAVELER -> Role.ROLE_TRAVELER;
            case HOTEL_OWNER -> Role.ROLE_HOTEL_OWNER;
        };

        var defaultRole = roleRepo.findByRole(role).orElseThrow();
        var entity = UserEntity.builder()
            .name(dto.name())
            .email(dto.email())
            .passwordHash(passwordEncoder.encode(dto.password()))
            .role(defaultRole)
            .build();
        entity = repo.saveAndFlush(entity);

        return userEntityToDto(entity);
    }

    public Role getCurrentUserRole() {
        try {
            return getCurrentUser().role();
        } catch (Exception e) {
            return Role.ROLE_ANONYMOUS;
        }
    }

    public UserResponseDto getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated())
            return null;

        if (auth.getPrincipal() instanceof UserEntity entity)
            return userEntityToDto(entity);

        return null;
    }

    public UserResponseDto getUserByEmail(String email) {
        var entity = repo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email, "User"));

        return userEntityToDto(entity);
    }

    public UserResponseDto getUserById(UUID id) {
        var entity = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "User"));

        return userEntityToDto(entity);
    }

    public UserResponseDto userEntityToDto(UserEntity entity) {
        return UserResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .role(entity.getRole().getRole())
            .build();
    }
}
