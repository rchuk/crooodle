package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.model.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HotelService {

    Map<RoomType, Integer> getAvailableRoomTypes(Hotel hotel);

    List<Room> getRooms(Hotel hotel);

    List<Hotel> filterHotels(List<Hotel> hotels, Map<String, Object> filters);
}
