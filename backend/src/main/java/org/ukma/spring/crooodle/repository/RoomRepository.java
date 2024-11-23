package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ukma.spring.crooodle.entities.RoomEntity;

import java.util.Optional;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    Optional<RoomEntity> findByIdAndHotelId(Long roomId, Long hotelId);

    List<RoomEntity> findByHotelId(Long hotelId);
}
