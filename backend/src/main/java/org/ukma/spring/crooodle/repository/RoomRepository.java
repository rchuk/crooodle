package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ukma.spring.crooodle.entities.RoomEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {

    Optional<RoomEntity> findByIdAndHotelId(int roomId, int hotelId);

    List<RoomEntity> findByHotelId(int hotelId);
}
