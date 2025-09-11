package org.ukma.spring.crooodle.roomsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;
import org.ukma.spring.crooodle.roomsvc.internal.RoomTypeEntity;
import org.ukma.spring.crooodle.roomsvc.internal.RoomTypeRepo;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomTypeSvc {

    private final RoomTypeRepo typeRepo;
    private final HotelRepo hotelRepo;

    public UUID create(@NotNull RoomTypeDto roomTypeDto){

        var newRoomType = RoomTypeEntity.builder()
                .name(roomTypeDto.name())
                .price(roomTypeDto.price())
                .build();

        newRoomType = typeRepo.save(newRoomType);
        return newRoomType.getId();
    }

    public RoomTypeDto read(@NotNull UUID roomTypeId){

        var roomType = typeRepo.findById(roomTypeId).orElseThrow(() -> new IllegalArgumentException("Room type is not found"));
        return RoomTypeDto.builder()
                .id(roomType.getId())
                .name(roomType.getName())
                .hotelId(roomType.getHotel().getId())
                .price(roomType.getPrice())
                .build();
    }

    public List<RoomTypeDto> readAllByHotel(@NotNull UUID hotelId){

        HotelEntity hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new IllegalArgumentException("Hotel type is not found"));
        List<RoomTypeEntity> typesForHotel = typeRepo.findAllByHotel(hotel);

        List<RoomTypeDto> typeDtos = new ArrayList<>();
        for(RoomTypeEntity re : typesForHotel){
            typeDtos.add(RoomTypeDto.builder()
                    .id(re.getId())
                    .hotelId(re.getHotel().getId())
                    .name(re.getName())
                    .price(re.getPrice())
                    .build());
        }

        return typeDtos;
    }

    public void update(@NotNull UUID roomTypeId, @NotNull RoomTypeDto roomTypeDto){

        var updatedRoomType = typeRepo.findById(roomTypeId).orElseThrow(() -> new IllegalArgumentException("Room type is not found"));
        updatedRoomType.setName(roomTypeDto.name());
        updatedRoomType.setPrice(roomTypeDto.price());
        typeRepo.save(updatedRoomType);
    }

    public void delete(@NotNull UUID roomTypeId){
        if(!typeRepo.existsById(roomTypeId)) throw new EntityNotFoundException(roomTypeId, "");

        typeRepo.deleteById(roomTypeId);
    }
}

