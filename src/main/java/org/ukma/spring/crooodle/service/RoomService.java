package org.ukma.spring.crooodle.service;

import jakarta.validation.Valid;
import org.ukma.spring.crooodle.dto.LoadRoomResponseDto;
import org.ukma.spring.crooodle.dto.RoomCrudRequestDto;
import org.ukma.spring.crooodle.dto.RoomCrudResponseDto;
import org.ukma.spring.crooodle.model.Room;

public interface RoomService {
    Room getRoom(long id);

    LoadRoomResponseDto loadRoom(long roomId);

    void updateRoom(@Valid RoomCrudRequestDto requestDto);

    void deleteRoom(long roomId);
}
