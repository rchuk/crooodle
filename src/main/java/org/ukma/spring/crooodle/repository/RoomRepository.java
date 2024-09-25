package org.ukma.spring.crooodle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.ukma.spring.crooodle.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAllByHotelId(Long hotelId, Pageable pageable);
}
