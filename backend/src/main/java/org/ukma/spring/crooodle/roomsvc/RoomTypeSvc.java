package org.ukma.spring.crooodle.roomsvc;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.roomsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.roomsvc.internal.RoomRepo;
import org.ukma.spring.crooodle.roomsvc.internal.RoomTypeEntity;
import org.ukma.spring.crooodle.roomsvc.internal.RoomTypeRepo;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomTypeSvc {

    private final RoomTypeRepo repo;

    UUID create(@NotNull RoomTypeDto roomTypeDto){

        var newRoomType = RoomTypeEntity.builder()
                .name(roomTypeDto.name())
                .price(roomTypeDto.price())
                .build();

        newRoomType = repo.save(newRoomType);
        return newRoomType.getId();
    }

    RoomTypeDto read(@NotNull UUID roomTypeId){

        var roomType = repo.findById(roomTypeId).orElseThrow(() -> new IllegalArgumentException("Room type is not found"));
        return RoomTypeDto.builder()
                .id(roomType.getId())
                .name(roomType.getName())
                .price(roomType.getPrice())
                .build();
    }

    void update(@NotNull UUID roomTypeId, @NotNull RoomTypeDto roomTypeDto){

        var updatedRoomType = repo.findById(roomTypeId).orElseThrow(() -> new IllegalArgumentException("Room type is not found"));
        updatedRoomType.setName(roomTypeDto.name());
        updatedRoomType.setPrice(roomTypeDto.price());
        repo.save(updatedRoomType);
    }

    void delete(@NotNull UUID roomTypeId){
        if(!repo.existsById(roomTypeId)) throw new IllegalArgumentException("Room type is not found");

        repo.deleteById(roomTypeId);
    }
}

