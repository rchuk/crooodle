package org.ukma.spring.crooodle.hotelsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.hotelsvc.entity.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.entity.RoomTypeEntity;

import java.util.List;
import java.util.UUID;

public interface RoomTypeRepo extends JpaRepository<RoomTypeEntity, UUID> {
    List<RoomTypeEntity> findAllByHotel(HotelEntity hotel);
}
