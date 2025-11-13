package org.ukma.spring.crooodle.usersvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.usersvc.dto.UserRole;
import org.ukma.spring.crooodle.usersvc.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByUserRole(UserRole userRole);
}
