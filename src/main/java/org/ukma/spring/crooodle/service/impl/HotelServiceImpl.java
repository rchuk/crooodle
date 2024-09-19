package org.ukma.spring.crooodle.service.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.model.RoomType;
import org.ukma.spring.crooodle.repository.HotelRepository;
import org.ukma.spring.crooodle.service.HotelService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {
    private HotelRepository hotelRepository;

    public void setHotelRepository(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel getHotel(long hotelId) {
        return hotelRepository.getById(hotelId);
    }

    // GET_HOTEL USE CASE
    /**
     * Since we call this use case on a point when we already have hotel objects loaded,
     * the only thing from business logic is to load data about room types and amount
     */
    @Override
    public Map<RoomType, Integer> getAvailableRoomTypes(long hotelId) {
        var hotel = hotelRepository.getById(hotelId);

        Map<RoomType, Integer> availableRoomTypes = new HashMap<>();

        for(Room room : hotel.getRooms()){
            // getOrDefault to write down new room if it doesn't exist, otherwise just add amount of availablerooms
            availableRoomTypes.put(room.getRoomType(), availableRoomTypes.getOrDefault(room.getRoomType(), 0) + 1);
        }

        return availableRoomTypes;
    }


    // GET_ROOMS USE CASE
    @Override
    public List<Room> listRooms(long hotelId) {
        return getHotel(hotelId).getRooms();
    }

    // FILTER_HOTELS USE CASE
    /**
     * Expects all list of hotels and map of filters name:value
     *
     * name of the field must correspond to the entity field names
     *
     * for ranging filters MaxField and MinField approaches should be used
     * (i.e. MaxPrice and MinPrice)
     * */
    @Override
    public List<Hotel> listHotels(Map<String, Object> filters) {
        return hotelRepository.list(filters);
    }
}
