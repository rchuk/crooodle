package org.ukma.spring.crooodle.reservationsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ukma.spring.crooodle.reservationsvc.ReservationState;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ReservationRepo extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findAllByRoomId(UUID roomId);
    @Query("""
      SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
      FROM ReservationEntity r
      WHERE r.roomId = :roomId
        AND r.checkInDate < :newCheckOutDate
        AND r.checkOutDate > :newCheckInDate
    """)
    boolean existsOverlappingReservation(
        @Param("roomId") UUID roomId,
        @Param("newCheckInDate") Date newCheckIn,
        @Param("newCheckOutDate") Date newCheckOut
    );
}
