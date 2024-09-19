package org.ukma.spring.crooodle.service.impl;

import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.model.RoomType;
import org.ukma.spring.crooodle.service.HotelService;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    // GET_HOTEL USE CASE
    /**
     * Since we call this use case on a point when we already have hotel objects loaded,
     * the only thing from business logic is to load data about room types and amount
     */
    @Override
    public Map<RoomType, Integer> getAvailableRoomTypes(Hotel hotel){

        Map<RoomType, Integer> availableRoomTypes = new HashMap<>();

        for(Room room : hotel.getRooms()){
            // getOrDefault to write down new room if it doesn't exist, otherwise just add amount of availablerooms
            availableRoomTypes.put(room.getRoomType(), availableRoomTypes.getOrDefault(room.getRoomType(), 0) + 1);
        }

        return availableRoomTypes;
    }


    // GET_ROOMS USE CASE
    @Override
    public List<Room> getRooms(Hotel hotel){
        return hotel.getRooms();
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
    public List<Hotel> filterHotels(List<Hotel> hotels, Map<String, Object> filters) {
        return hotels.stream()
                .filter(hotel -> applyFilters(hotel, filters))
                .collect(Collectors.toList());
    }

    private boolean applyFilters(Hotel hotel, Map<String, Object> filters) {
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String fieldName = filter.getKey();
            Object expectedValue = filter.getValue();

            try {
                if(fieldName.contains("Min")){
                    fieldName = fieldName.replace("Min", "");
                    Field field = hotel.getClass().getDeclaredField(fieldName);

                    field.setAccessible(true);
                    Object actualValue = field.get(hotel);

                    if (expectedValue instanceof Number && actualValue instanceof Number) {
                        double minValue = ((Number) expectedValue).doubleValue();
                        double actual = ((Number) actualValue).doubleValue();

                        if (actual < minValue) {
                            return false;
                        }
                    }
                    else
                        return false;

                } else  if (fieldName.contains("Max")) {
                    fieldName = fieldName.replace("Max", "");
                    Field field = hotel.getClass().getDeclaredField(fieldName);

                    field.setAccessible(true);
                    Object actualValue = field.get(hotel);

                    if (expectedValue instanceof Number && actualValue instanceof Number) {
                        double maxValue = ((Number) expectedValue).doubleValue();
                        double actual = ((Number) actualValue).doubleValue();

                        if (actual > maxValue) {
                            return false;
                        }
                    } else
                        return false;
                }
                else {
                    Field field = hotel.getClass().getDeclaredField(fieldName);

                    field.setAccessible(true);
                    Object actualValue = field.get(hotel);

                    if (!expectedValue.equals(actualValue)) {
                        return false;
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true; // Otherwise everything is good
    }

}
