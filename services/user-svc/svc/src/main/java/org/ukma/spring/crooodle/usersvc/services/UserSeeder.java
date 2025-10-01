package org.ukma.spring.crooodle.usersvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.entities.RoleEntity;
import org.ukma.spring.crooodle.usersvc.entities.UserEntity;
import org.ukma.spring.crooodle.usersvc.repositories.RoleRepo;
import org.ukma.spring.crooodle.usersvc.repositories.UserRepo;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class UserSeeder implements ApplicationRunner {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${auth.admin.username}")
    String adminUsername;
    @Value("${auth.admin.password}")
    String adminPassword;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        seedRoles();
        seedAdmin();
    }

    private void seedAdmin() {
        if (userRepo.findByEmail(adminUsername).isEmpty()) {
            var role = roleRepo.findByRole(Role.ROLE_ADMIN).orElseThrow();

            var user = UserEntity.builder()
                .email(adminUsername)
                .passwordHash(passwordEncoder.encode(adminPassword))
                .name("[Super Administrator]")
                .role(role)
                .build();
            userRepo.saveAndFlush(user);
        }
    }

    private void seedRoles() {
        Consumer<Role> seedRole = role -> {
            if (roleRepo.findByRole(role).isEmpty())
                roleRepo.save(RoleEntity.builder().role(role).build());
        };

        List.of(Role.values()).forEach(seedRole);
        roleRepo.flush();
    }
}
