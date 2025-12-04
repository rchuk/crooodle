package org.ukma.spring.crooodle.reservationsvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ukma.spring.crooodle.reservationsvc.entity.ReservationEntity;

import java.time.LocalDate;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {
	@Query("""
        SELECT COUNT(r) > 0
        FROM ReservationEntity r
        WHERE r.roomId = :roomId
        AND (
            (r.checkInDate <= :checkOut AND r.checkOutDate >= :checkIn)
        )
    """)
	boolean existsOverlappingReservation(
		@Param("roomId") UUID roomId,
		@Param("checkIn") LocalDate checkIn,
		@Param("checkOut") LocalDate checkOut
	);

	Page<ReservationEntity> findAllBy(Specification<ReservationEntity> spec, Pageable pageable);
}
