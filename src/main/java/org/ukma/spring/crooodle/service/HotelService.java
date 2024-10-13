package org.ukma.spring.crooodle.service;

import org.springframework.data.domain.Page;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.model.grouped.RoomTypeWithCount;

import java.util.List;

public interface HotelService {
    long createHotel(HotelCreateRequestDto requestDto);

    void updateHotel(long id, HotelUpdateRequestDto requestDto);

    HotelResponseDto getHotel(long id);

    void deleteHotel(long id);

    List<RoomTypeWithCountResponseDto> getAvailableRoomTypes(long hotelId);

    Page<Room> listRooms(long hotelId, PaginationDto paginationDto);

    // TODO: Pass criteria DTO
    Page<HotelResponseDto> listHotels(PaginationDto paginationDto);

    WeatherForecastResponseDto getHotelWeatherForecast(long hotelId);

    Hotel getHotelEntity(long id);
}
