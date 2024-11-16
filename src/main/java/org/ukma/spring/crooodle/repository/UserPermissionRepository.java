package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.entities.UserPermissionEntity;
import org.ukma.spring.crooodle.entities.enums.UserPermissionKindEntity;

import java.util.Optional;

public interface UserPermissionRepository extends JpaRepository<UserPermissionEntity, Integer> {
    Optional<UserPermissionEntity> findByName(UserPermissionKindEntity name);
}
