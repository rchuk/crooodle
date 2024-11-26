package org.ukma.spring.crooodle;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.entities.enums.UserPermission;
import org.ukma.spring.crooodle.service.AuthService;
import org.ukma.spring.crooodle.service.TestDataSeederService;
import org.ukma.spring.crooodle.service.UserPermissionSeeder;
import org.ukma.spring.crooodle.service.UserService;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class CrooodleCommandLineRunner implements CommandLineRunner {
    private final UserPermissionSeeder userPermissionSeeder;
    private final TestDataSeederService testDataSeederService;

    private final AuthService authService;
    private final UserService userService;

    @Value("${app.admin.email}")
    private String adminEmail;
    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String...args) {
        tryRun(t1 -> userPermissionSeeder.seed());

        tryRun(t1 -> {
            authService.register(UserRegisterRequestDto.builder()
                .name("Admin")
                .email(adminEmail)
                .password(adminPassword)
                .build()
            );
        });

        tryRun(t1 -> {
            for (var permission : UserPermission.values())
                userService.addPermission(adminEmail, permission);
        });

        tryRun(t1 -> testDataSeederService.seed());
    }

    private void tryRun(Consumer<Void> fn) {
        try {
            fn.accept(null);
        } catch (Exception ee) {}
    }
}
