package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.WeatherForecastResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.model.Hotel;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.model.grouped.RoomTypeWithCount;
import org.ukma.spring.crooodle.repository.HotelRepository;
import org.ukma.spring.crooodle.repository.RoomRepository;
import org.ukma.spring.crooodle.service.HotelService;
import org.ukma.spring.crooodle.service.WeatherService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {
    private final WeatherService weatherService;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;

    @Autowired
    public void setHotelRepository(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel getHotel(long hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow();
    }

    // GET_HOTEL USE CASE
    /**
     * Since we call this use case on a point when we already have hotel objects loaded,
     * the only thing from business logic is to load data about room types and amount
     */
    @Override
    public List<RoomTypeWithCount> getAvailableRoomTypes(long hotelId) {
        return hotelRepository.getRoomTypesWithCount(hotelId);
    }


    // GET_ROOMS USE CASE
    @Override
    public Page<Room> listRooms(long hotelId, PaginationDto paginationDto) {
        return roomRepository.findAllByHotelId(hotelId, paginationDto.toPageable());
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
    public Page<Hotel> listHotels(PaginationDto paginationDto) {
        // TODO: Add specification
        return hotelRepository.findAll(paginationDto.toPageable());
    }

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void updateHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(long hotelId) {
        hotelRepository.deleteById(hotelId);
    }

    @Override
    public void createHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public WeatherForecastResponseDto getHotelWeatherForecast(long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(PublicNotFoundException::new);

        return weatherService.getWeatherForecast(hotel.getLatitude(), hotel.getLongitude());
    }
}
