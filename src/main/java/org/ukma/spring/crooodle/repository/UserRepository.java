package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
