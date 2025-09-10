package org.ukma.spring.crooodle.reservationsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.roomsvc.internal.RoomEntity;

import java.util.List;
import java.util.UUID;

public interface ReservationRepo extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findAllByRoom (RoomEntity room);
}
