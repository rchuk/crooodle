package org.ukma.spring.crooodle.service;

import org.springframework.data.domain.Page;
import org.ukma.spring.crooodle.dto.WeatherForecastResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.model.grouped.RoomTypeWithCount;

import java.util.List;

public interface HotelService {
    Hotel getHotel(long hotelId);

    void updateHotel(Hotel hotel);

    void deleteHotel(long hotelId);

    void createHotel(Hotel hotel);

    List<RoomTypeWithCount> getAvailableRoomTypes(long hotelId);

    Page<Room> listRooms(long hotelId, PaginationDto paginationDto);

    // TODO: Pass criteria DTO
    Page<Hotel> listHotels(PaginationDto paginationDto);

    WeatherForecastResponseDto getHotelWeatherForecast(long hotelId);
}
