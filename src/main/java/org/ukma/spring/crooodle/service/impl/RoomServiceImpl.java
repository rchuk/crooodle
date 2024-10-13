package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.RoomResponseDto;
import org.ukma.spring.crooodle.dto.RoomUpdateRequestDto;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.repository.RoomRepository;
import org.ukma.spring.crooodle.service.RoomService;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public Room getRoom(long id) {
        return null;
    }

    public RoomResponseDto loadRoom(long roomId) {
        var room = roomRepository.findById(roomId).orElseThrow(PublicNotFoundException::new);

        return buildRoomResponseDto(room);
    }

    @Override
    public void updateRoom(long id, RoomUpdateRequestDto requestDto) {
        var room = roomRepository.findById(id).orElseThrow(PublicNotFoundException::new);

        if (requestDto.getNumber() != null)
            room.setNumber(requestDto.getNumber());
        if (requestDto.getPricePerNight() != null)
            room.setPricePerNight(requestDto.getPricePerNight());

        roomRepository.saveAndFlush(room);
    }

    @Override
    public void deleteRoom(long id) {
        roomRepository.deleteById(id);
    }

    public RoomResponseDto buildRoomResponseDto(Room room) {
        return RoomResponseDto.builder()
                .id(room.getId())
                .number(room.getNumber())
                .pricePerNight(room.getPricePerNight())
                .roomType(room.getRoomType().getType())
                .build();
    }
}
