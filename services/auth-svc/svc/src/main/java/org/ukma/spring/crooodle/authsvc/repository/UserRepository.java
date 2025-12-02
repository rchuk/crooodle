package org.ukma.spring.crooodle.authsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.authsvc.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	Optional<UserEntity> findByUsername(String username);
	boolean existsByUsername(String username);
}
