package org.ukma.spring.crooodle.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.entities.HotelEntity;
import org.ukma.spring.crooodle.entities.RoomEntity;
import org.ukma.spring.crooodle.repository.HotelRepository;
import org.ukma.spring.crooodle.repository.RoomRepository;
import org.ukma.spring.crooodle.service.RoomService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public int create(Long hotelId, RoomCreateRequestDto requestDto) {
        HotelEntity hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));

        RoomEntity room = RoomEntity.builder()
            .name(requestDto.getName())
            .capacity(requestDto.getCapacity())
            .pricePerNight(requestDto.getPricePerNight())
            .description(requestDto.getDescription())
            .available(requestDto.isAvailable())
            .hotel(hotel)
            .build();

        room = roomRepository.save(room);
        return room.getId().intValue(); // Повертаємо int, якщо це необхідно
    }


    @Override
    public RoomAdminResponseDto getAdmin(int hotelId, int roomId) {
        RoomEntity room = roomRepository.findByIdAndHotelId(roomId, hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found in the given hotel"));

        return mapToAdminResponseDto(room);
    }

    @Override
    public void edit(int hotelId, int roomId, RoomEditRequestDto requestDto) {
        RoomEntity room = roomRepository.findByIdAndHotelId(roomId, hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found in the given hotel"));

        room.setName(requestDto.getName());
        room.setCapacity(requestDto.getCapacity());
        room.setPricePerNight(requestDto.getPricePerNight());
        room.setDescription(requestDto.getDescription());
        room.setAvailable(requestDto.isAvailable());

        roomRepository.save(room);
    }

    @Override
    public void delete(int hotelId, int roomId) {
        RoomEntity room = roomRepository.findByIdAndHotelId(roomId, hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found in the given hotel"));

        roomRepository.delete(room);
    }

    @Override
    public PageResponseDto<RoomAdminResponseDto> listAdmin(int hotelId, RoomCriteriaDto criteriaDto) {
        List<RoomEntity> rooms = roomRepository.findByHotelId(hotelId);

        List<RoomAdminResponseDto> roomDtos = rooms.stream()
                .map(this::mapToAdminResponseDto)
                .collect(Collectors.toList());

        return PageResponseDto.<RoomAdminResponseDto>builder()
                .total(roomDtos.size())
                .items(roomDtos)
                .build();
    }

    @Override
    public RoomResponseDto get(Long hotelId, Long roomId) {
        RoomEntity room = roomRepository.findByIdAndHotelId(roomId, hotelId)
            .orElseThrow(() -> new IllegalArgumentException("Room not found in the given hotel"));
        return mapToResponseDto(room);
    }


    @Override
    public PageResponseDto<RoomResponseDto> list(int hotelId, @Valid RoomCriteriaDto criteriaDto) {
        List<RoomEntity> rooms = roomRepository.findByHotelId(hotelId);

        List<RoomResponseDto> roomDtos = rooms.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());

        return PageResponseDto.<RoomResponseDto>builder()
                .total(roomDtos.size())
                .items(roomDtos)
                .build();
    }

    private RoomAdminResponseDto mapToAdminResponseDto(RoomEntity room) {
        return RoomAdminResponseDto.builder()
                .id(room.getId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .pricePerNight(room.getPricePerNight())
                .description(room.getDescription())
                .available(room.isAvailable())
                .hotelName(room.getHotel().getName())
                .build();
    }

    private RoomResponseDto mapToResponseDto(RoomEntity room) {
        return RoomResponseDto.builder()
                .id(room.getId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .pricePerNight(room.getPricePerNight())
                .description(room.getDescription())
                .available(room.isAvailable())
                .build();
    }
}
