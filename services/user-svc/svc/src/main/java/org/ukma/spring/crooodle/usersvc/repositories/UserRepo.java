package org.ukma.spring.crooodle.usersvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.usersvc.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
