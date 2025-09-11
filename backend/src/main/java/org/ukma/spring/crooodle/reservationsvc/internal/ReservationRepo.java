package org.ukma.spring.crooodle.reservationsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.reservationsvc.ReservationState;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.usersvc.internal.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ReservationRepo extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findAllByRoom(RoomEntity room);
    List<ReservationEntity> findAllByRoomAndStateAndCheckin(RoomEntity room, ReservationState state, Date checkinDate);
    List<ReservationEntity> findAllByUser(UserEntity user);
    List<ReservationEntity> findAllByUserAndCheckinAndCheckout (UserEntity user, Date checkIn, Date checkOut);
    List<ReservationEntity> findAllByUserAndState (UserEntity user, ReservationState state);
    boolean existsByRoomAndCheckinAndState(RoomEntity room, Date checkinDate, ReservationState state);
}
