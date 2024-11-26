package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.entities.UserPermissionEntity;
import org.ukma.spring.crooodle.entities.enums.UserPermission;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserPermissionRepository extends JpaRepository<UserPermissionEntity, Integer> {
    Optional<UserPermissionEntity> findByKind(UserPermission kind);
    List<UserPermissionEntity> findAllByKindIn(Set<UserPermission> kinds);
}
