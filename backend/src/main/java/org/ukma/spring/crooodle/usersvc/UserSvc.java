package org.ukma.spring.crooodle.usersvc;

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
import org.ukma.spring.crooodle.utils.exceptions.CrooodleException;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;
import org.ukma.spring.crooodle.utils.exceptions.InvalidRequestException;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserSvc implements UserDetailsService {
    private final UserRepo repo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Transactional
    public UserDto register(UserRegisterDto dto) {
        if (repo.existsByEmail(dto.email()))
            throw new InvalidRequestException("User with the following email already exists");

        var role = switch (dto.role()) {
            case TRAVELER -> Role.ROLE_TRAVELER;
            case HOTEL_OWNER ->  Role.ROLE_HOTEL_OWNER;
        };

        var defaultRole = roleRepo.findByRole(role).orElseThrow();
        var entity = UserEntity.builder()
            .name(dto.name())
            .email(dto.email())
            .passwordHash(passwordEncoder.encode(dto.password()))
            .role(defaultRole)
            .build();
        entity = repo.save(entity);

        return userEntityToDto(entity);
    }

    public Role getCurrentUserRole() {
        try {
            return getCurrentUser().role();
        } catch (Exception e) {
            return Role.ROLE_ANONYMOUS;
        }
    }

    public UserDto getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated())
            return null;

        if (auth.getPrincipal() instanceof UserEntity entity)
            return userEntityToDto(entity);

        return null;
    }

    public UserDto getUserByEmail(String email) {
        var entity = repo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email, "User"));

        return userEntityToDto(entity);
    }

    public UserDto getUserById(UUID id) {
        var entity = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "User"));

        return userEntityToDto(entity);
    }

    public UserDto userEntityToDto(UserEntity entity) {
        return UserDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .role(entity.getRole().getRole())
            .build();
    }
}
