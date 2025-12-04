package org.ukma.spring.crooodle.reservationsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.reservationsvc.entity.ReservationEntity;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {}
