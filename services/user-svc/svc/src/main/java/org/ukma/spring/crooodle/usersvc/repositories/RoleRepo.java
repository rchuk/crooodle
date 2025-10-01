package org.ukma.spring.crooodle.usersvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.entities.RoleEntity;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRole(Role role);
}
