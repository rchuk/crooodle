package org.ukma.spring.crooodle.hotelsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoomTypeRepo extends JpaRepository<RoomTypeEntity, UUID> {
    List<RoomTypeEntity> findAllByHotel(HotelEntity hotel);
}
