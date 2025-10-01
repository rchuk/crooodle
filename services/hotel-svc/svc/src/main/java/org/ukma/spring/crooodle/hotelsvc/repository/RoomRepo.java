package org.ukma.spring.crooodle.hotelsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.hotelsvc.entity.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.entity.RoomEntity;
import org.ukma.spring.crooodle.hotelsvc.entity.RoomTypeEntity;

import java.util.List;
import java.util.UUID;

public interface RoomRepo extends JpaRepository<RoomEntity, UUID> {

    List<RoomEntity> findAllByType_Hotel(HotelEntity hotel);

    List<RoomEntity> findAllByType(RoomTypeEntity type);
    long countAllByType_Hotel(HotelEntity hotel);
}
