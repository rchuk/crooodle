package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.model.RoomType;

import java.util.List;
import java.util.Map;

public interface HotelService {
    Hotel getHotel(long hotelId);

    Map<RoomType, Integer> getAvailableRoomTypes(long hotelId);

    List<Room> listRooms(long hotelId);

    List<Hotel> listHotels(Map<String, Object> filters);
}
