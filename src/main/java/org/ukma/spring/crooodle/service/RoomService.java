package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.LoadRoomResponseDto;
import org.ukma.spring.crooodle.model.Room;

public interface RoomService {
    Room getRoom(long id);

    LoadRoomResponseDto loadRoom(long roomId);

    void updateRoom(Room room);

    void deleteRoom(long roomId);
}
