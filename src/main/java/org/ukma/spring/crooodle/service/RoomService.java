package org.ukma.spring.crooodle.service;

import jakarta.validation.Valid;
import org.ukma.spring.crooodle.dto.LoadRoomResponseDto;
import org.ukma.spring.crooodle.dto.RoomCrudRequestDto;
import org.ukma.spring.crooodle.dto.RoomCrudResponseDto;
import org.ukma.spring.crooodle.model.Room;

public interface RoomService {
    Room getRoom(long id);

    LoadRoomResponseDto loadRoom(long roomId);

    RoomCrudResponseDto updateRoom(@Valid RoomCrudRequestDto requestDto);

    RoomCrudResponseDto deleteRoom(long roomId);
}
