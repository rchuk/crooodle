package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.*;
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
    public HotelResponseDto getHotel(long hotelId) {
        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(PublicNotFoundException::new);

        return buildHotelResponseDto(hotel);
    }

    // GET_HOTEL USE CASE
    /**
     * Since we call this use case on a point when we already have hotel objects loaded,
     * the only thing from business logic is to load data about room types and amount
     */
    @Override
    public List<RoomTypeWithCountResponseDto> getAvailableRoomTypes(long hotelId) {
        return hotelRepository.getRoomTypesWithCount(hotelId)
                .stream()
                .map(this::buildRoomTypeWithCountResponseDto)
                .toList();
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
    public Page<HotelResponseDto> listHotels(PaginationDto paginationDto) {
        // TODO: Add specification
        return hotelRepository.findAll(paginationDto.toPageable())
                .map(this::buildHotelResponseDto);
    }

    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void updateHotel(long id, HotelUpdateRequestDto requestDto) {
        var hotel = hotelRepository.findById(id)
            .orElseThrow(PublicNotFoundException::new);

        if (requestDto.getName() != null)
            hotel.setName(requestDto.getName());
        if (requestDto.getAddress() != null)
            hotel.setAddress(requestDto.getAddress());
        if (requestDto.getLatitude() != null)
            hotel.setLatitude(requestDto.getLatitude());
        if (requestDto.getLongitude() != null)
            hotel.setLongitude(requestDto.getLongitude());

        hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(long hotelId) {
        hotelRepository.deleteById(hotelId);
    }

    @Override
    public long createHotel(HotelCreateRequestDto requestDto) {
        var hotel = Hotel.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .ranking(0.0)
                .totalRanks(0)
                .build();

        return hotelRepository.saveAndFlush(hotel).getId();
    }

    @Override
    public WeatherForecastResponseDto getHotelWeatherForecast(long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow(PublicNotFoundException::new);

        return weatherService.getWeatherForecast(hotel.getLatitude(), hotel.getLongitude());
    }

    @Override
    public Hotel getHotelEntity(long id) {
        return hotelRepository.findById(id).orElseThrow(PublicNotFoundException::new);
    }

    public RoomTypeWithCountResponseDto buildRoomTypeWithCountResponseDto(RoomTypeWithCount roomTypeWithCount) {
        return RoomTypeWithCountResponseDto.builder()
                .roomType(roomTypeWithCount.type().getType())
                .count(roomTypeWithCount.count())
                .build();
    }

    public HotelResponseDto buildHotelResponseDto(Hotel hotel) {
        return HotelResponseDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .ranking(hotel.getRanking())
                .totalRanks(hotel.getTotalRanks())
                .latitude(hotel.getLatitude())
                .longitude(hotel.getLongitude())
                .build();
    }
    @Override
    public List<HotelResponseDto> listAllHotels() {
        return hotelRepository.findAll().stream()
                .map(this::buildHotelResponseDto)
                .toList();
    }
}
