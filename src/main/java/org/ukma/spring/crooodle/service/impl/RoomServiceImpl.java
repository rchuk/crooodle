package org.ukma.spring.crooodle.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.LoadRoomResponseDto;
import org.ukma.spring.crooodle.dto.RoomCrudRequestDto;
import org.ukma.spring.crooodle.dto.RoomCrudResponseDto;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.service.RoomService;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    @Override
    public Room getRoom(long id) {
        return null;
    }

    public LoadRoomResponseDto loadRoom(long roomId) {
        return null;
    }

    @Override
    public RoomCrudResponseDto updateRoom(@Valid RoomCrudRequestDto requestDto) {

        //TODO: implement necessary logic

        return new RoomCrudResponseDto();
    }

    @Override
    public RoomCrudResponseDto deleteRoom(long roomId) {

        //TODO: implement necessary logic

        return new RoomCrudResponseDto();
    }

}
