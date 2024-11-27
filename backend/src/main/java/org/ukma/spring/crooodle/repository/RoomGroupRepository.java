package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.ukma.spring.crooodle.entities.RoomGroupEntity;

public interface RoomGroupRepository extends JpaRepository<RoomGroupEntity, Long>, JpaSpecificationExecutor<RoomGroupEntity> {
}
