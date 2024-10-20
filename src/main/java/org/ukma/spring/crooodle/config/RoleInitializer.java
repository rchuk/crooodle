package org.ukma.spring.crooodle.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.model.UserRole;
import org.ukma.spring.crooodle.model.enums.UserRoleKind;
import org.ukma.spring.crooodle.repository.UserRoleRepository;

@Component
public class RoleInitializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;

    public RoleInitializer(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) {
        if (userRoleRepository.findByName(UserRoleKind.USER).isEmpty()) {
            userRoleRepository.save(new UserRole(null, UserRoleKind.USER, null));
        }
        if (userRoleRepository.findByName(UserRoleKind.ADMIN).isEmpty()) {
            userRoleRepository.save(new UserRole(null, UserRoleKind.ADMIN, null));
        }
    }
}
