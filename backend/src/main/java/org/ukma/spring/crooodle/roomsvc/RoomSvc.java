package org.ukma.spring.crooodle.roomsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;
import org.ukma.spring.crooodle.roomsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.roomsvc.internal.RoomRepo;
import org.ukma.spring.crooodle.roomsvc.internal.RoomTypeEntity;
import org.ukma.spring.crooodle.roomsvc.internal.RoomTypeRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomSvc {

    private final RoomRepo repo;
    private final RoomTypeRepo typeRepo;
    private final HotelRepo hotelRepo;
    private final RoomRepo roomRepo;

    public UUID create(@NotNull RoomDto roomDto){

        RoomTypeEntity roomType = typeRepo.findById(roomDto.typeId()).orElseThrow(() -> new IllegalArgumentException("Room type is not found"));
        HotelEntity hotel = hotelRepo.findById(roomDto.hotelId()).orElseThrow(() -> new IllegalArgumentException("Hotel is not found"));

        var newRoom = RoomEntity.builder()
                .number(roomDto.number())
                .type(roomType)
                .hotel(hotel)
                .isOccupied(false)
                .build();

        newRoom = repo.save(newRoom);
        return newRoom.getId();
    }

    public RoomDto read(@NotNull UUID roomId){

        RoomEntity room = repo.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room is not found"));
        return RoomDto.builder()
                .id(room.getId())
                .number(room.getNumber())
                .typeId(room.getType().getId())
                .hotelId(room.getHotel().getId())
                .isOccupied(room.isOccupied())
                .build();
    }

    public List<RoomDto> readAllByHotel(@NotNull UUID hotelId){

        HotelEntity hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new IllegalArgumentException("Hotel is not found"));
        List<RoomEntity> roomsByHotel = roomRepo.findAllByHotel(hotel);
        List<RoomDto> roomsDTO = new ArrayList<>();

        for(RoomEntity re : roomsByHotel){
            roomsDTO.add(RoomDto.builder()
                        .id(re.getId())
                        .number(re.getNumber())
                        .isOccupied(re.isOccupied())
                        .build());
        }

        return roomsDTO;
    }

    public List<RoomDto> readAllByType(@NotNull UUID hotelId, @NotNull UUID typeId){

        HotelEntity hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new IllegalArgumentException("Hotel is not found"));
        RoomTypeEntity type = typeRepo.findById(typeId).orElseThrow(() -> new IllegalArgumentException("Room type is not found"));

        List<RoomEntity> roomsByHotel = roomRepo.findAllByHotelAndType(hotel, type);
        List<RoomDto> roomsDTO = new ArrayList<>();

        for(RoomEntity re : roomsByHotel){
            roomsDTO.add(RoomDto.builder()
                    .id(re.getId())
                    .number(re.getNumber())
                    .isOccupied(re.isOccupied())
                    .build());
        }

        return roomsDTO;
    }


    public void update(@NotNull UUID roomId, @NotNull RoomDto roomDto) {

        RoomTypeEntity roomType = typeRepo.findById(roomDto.typeId()).orElseThrow(() -> new IllegalArgumentException("Room type is not found"));
        HotelEntity hotel = hotelRepo.findById(roomDto.hotelId()).orElseThrow(() -> new IllegalArgumentException("Hotel is not found"));

        var updatedRoom = repo.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room is not found"));
        updatedRoom.setNumber(roomDto.number());
        updatedRoom.setType(roomType);
        updatedRoom.setHotel(hotel);
        repo.save(updatedRoom);
    }

    public void delete(@NotNull UUID roomId){
        if(!repo.existsById(roomId))
            throw new IllegalArgumentException("Room is not found");

        repo.deleteById(roomId);
    }

}
