package org.ukma.spring.crooodle.hotelsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ukma.spring.crooodle.hotelsvc.entity.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.entity.RoomEntity;
import org.ukma.spring.crooodle.hotelsvc.entity.RoomTypeEntity;

import java.util.List;
import java.util.UUID;

public interface RoomRepo extends JpaRepository<RoomEntity, UUID> {

    List<RoomEntity> findAllByType_Hotel(HotelEntity hotel);

    List<RoomEntity> findAllByType(RoomTypeEntity type);
		List<RoomEntity> findAllByType_HotelIdAndType(UUID hotelId, RoomTypeEntity rt);
    long countAllByType_Hotel(HotelEntity hotel);

	@Query("""
        SELECT r FROM RoomEntity r
        JOIN FETCH r.type t
        JOIN FETCH t.hotel h
        WHERE h.id = :hotelId
        """)
	List<RoomEntity> findAllByHotelIdFetchAll(@Param("hotelId") UUID hotelId);

	@Query("""
        SELECT r FROM RoomEntity r
        JOIN FETCH r.type t
        JOIN FETCH t.hotel h
        WHERE t.id = :typeId
        """)
	List<RoomEntity> findAllByTypeIdFetchAll(@Param("typeId") UUID typeId);

	@Query("""
        SELECT r FROM RoomEntity r
        JOIN FETCH r.type t
        JOIN FETCH t.hotel h
        WHERE h.id = :hotelId AND t.id = :typeId
        """)
	List<RoomEntity> findAllByHotelAndTypeFetchAll(
		@Param("hotelId") UUID hotelId,
		@Param("typeId") UUID typeId
	);

}
