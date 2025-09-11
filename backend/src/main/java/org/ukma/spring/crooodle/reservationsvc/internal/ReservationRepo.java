package org.ukma.spring.crooodle.reservationsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.reservationsvc.ReservationState;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ReservationRepo extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findAllByRoomId(UUID roomId);

    List<ReservationEntity> findAllByRoomIdAndStateAndCheckin(UUID roomId, ReservationState state, Date checkinDate);

    List<ReservationEntity> findAllByUserId(UUID userId);

    List<ReservationEntity> findAllByUserIdAndCheckinAndCheckout(UUID userId, Date checkIn, Date checkOut);

    List<ReservationEntity> findAllByUserIdAndState(UUID userId, ReservationState state);

    boolean existsByRoomIdAndCheckinAndState(UUID roomId, Date checkinDate, ReservationState state);
}
