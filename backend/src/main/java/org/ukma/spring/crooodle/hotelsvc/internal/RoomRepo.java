package org.ukma.spring.crooodle.hotelsvc.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoomRepo extends JpaRepository<RoomEntity, UUID> {

    List<RoomEntity> findAllByHotel(HotelEntity hotel);
    List<RoomEntity> findAllByHotelAndType(HotelEntity hotel, RoomTypeEntity type);
}
