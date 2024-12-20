package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.ukma.spring.crooodle.entities.WorldRegionEntity;

import java.util.Optional;

public interface WorldRegionRepository extends JpaRepository<WorldRegionEntity, Integer>, JpaSpecificationExecutor<WorldRegionEntity> {
    Optional<WorldRegionEntity> findByName(String name);
}
