package org.ukma.spring.crooodle.hotelsvc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.hotelsvc.entity.RoomEntity;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {
	Page<RoomEntity> findAllByHotelId(UUID hotelId, Pageable pageable);
}
