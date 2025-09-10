package org.ukma.spring.crooodle.usersvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.usersvc.Role;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRole(Role role);
}
