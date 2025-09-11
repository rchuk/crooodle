package org.ukma.spring.crooodle.roomsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;

import java.util.List;
import java.util.UUID;

public interface RoomTypeRepo extends JpaRepository<RoomTypeEntity, UUID> {

    List<RoomTypeEntity> findAllByHotel (HotelEntity hotel);
}
