package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.entities.UserPermissionEntity;
import org.ukma.spring.crooodle.entities.enums.UserPermission;
import org.ukma.spring.crooodle.repository.UserPermissionRepository;
import org.ukma.spring.crooodle.service.UserPermissionSeeder;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class UserPermissionSeederImpl implements UserPermissionSeeder {
    private final UserPermissionRepository repository;

    @Override
    public void seed() {
        var entities = Arrays.stream(UserPermission.values())
            .map(permission -> UserPermissionEntity.builder().kind(permission).build())
            .toList();
        repository.saveAllAndFlush(entities);
    }
}
