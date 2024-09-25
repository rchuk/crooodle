package org.ukma.spring.crooodle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.grouped.RoomTypeWithCount;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    @Query(value = """
        SELECT
            new org.ukma.spring.crooodle.model.grouped.RoomTypeWithCount(room.roomType, COUNT(room))
        FROM Hotel hotel
        JOIN Room room ON hotel = room.hotel
        JOIN RoomType roomType ON room.roomType = roomType
        WHERE hotel.id = :hotelId
        GROUP BY roomType
        """
    )
    List<RoomTypeWithCount> getRoomTypesWithCount(@Param("hotelId") Long hotelId);
}
