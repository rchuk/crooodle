package org.ukma.spring.crooodle.roomsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.roomsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.roomsvc.internal.RoomRepo;
import org.ukma.spring.crooodle.roomsvc.internal.RoomTypeEntity;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomSvc {

    private final RoomRepo repo;

    UUID create(@NotNull RoomDto roomDto, @NotNull RoomTypeEntity roomTypeEntity){

        var newRoom = RoomEntity.builder()
                .number(roomDto.number())
                .type(roomTypeEntity)
                .isOccupied(false)
                .build();

        newRoom = repo.save(newRoom);
        return newRoom.getId();
    }

    RoomDto read(@NotNull UUID roomId){

        var room = repo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room is not found"));
        return RoomDto.builder()
                .id(room.getId())
                .number(room.getNumber())
                .isOccupied(room.isOccupied())
                .build();
    }


    void update(@NotNull UUID roomId, @NotNull RoomDto roomDto, @NotNull RoomTypeEntity roomTypeEntity) {
        var updatedRoom = repo.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room is not found"));
        updatedRoom.setNumber(roomDto.number());
        updatedRoom.setType(roomTypeEntity);
        repo.save(updatedRoom);
    }

    void delete(@NotNull UUID roomId){
        if(!repo.existsById(roomId))
            throw new IllegalArgumentException("Room is not found");

        repo.deleteById(roomId);
    }

}
