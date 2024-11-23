package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ukma.spring.crooodle.entities.RoomTypeEntity;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomTypeEntity, Long> {

    @Query("SELECT rt.name AS roomType, COUNT(r.id) AS roomCount " +
            "FROM RoomEntity r " +
            "JOIN r.roomType rt " +
            "WHERE r.hotel.id = :hotelId " +
            "GROUP BY rt.name")
    List<Object[]> findRoomCountsByHotel(@Param("hotelId") Long hotelId);

}
