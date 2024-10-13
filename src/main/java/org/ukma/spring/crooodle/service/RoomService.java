package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.RoomResponseDto;
import org.ukma.spring.crooodle.dto.RoomUpdateRequestDto;
import org.ukma.spring.crooodle.model.Room;

public interface RoomService {
    Room getRoom(long id);

    RoomResponseDto loadRoom(long roomId);

    void updateRoom(long roomId, RoomUpdateRequestDto requestDto);

    void deleteRoom(long roomId);
}
