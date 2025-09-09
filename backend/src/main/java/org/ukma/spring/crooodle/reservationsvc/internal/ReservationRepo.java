package org.ukma.spring.crooodle.reservationsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepo extends JpaRepository<ReservationEntity, UUID> {

}
