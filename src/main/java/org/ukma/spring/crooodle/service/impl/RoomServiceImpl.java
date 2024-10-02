package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.service.RoomService;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    @Override
    public Room getRoom(long id) {
        return null;
    }

    @Override
    public void updateRoom(Room room) {
        //TODO: implement necessary logic
    }

    @Override
    public void deleteRoom(long roomId) {
        //TODO: implement necessary logic
    }

}
