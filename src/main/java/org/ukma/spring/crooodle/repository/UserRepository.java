package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
