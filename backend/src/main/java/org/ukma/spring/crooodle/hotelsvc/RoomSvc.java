package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomRepo;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomTypeEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomTypeRepo;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomSvc {

    private final RoomTypeRepo typeRepo;
    private final HotelRepo hotelRepo;
    private final RoomRepo roomRepo;

    public UUID create(@NotNull RoomDto roomDto){

        RoomTypeEntity roomType = typeRepo.findById(roomDto.typeId()).orElseThrow(() -> new EntityNotFoundException(roomDto.typeId() ,""));

        var newRoom = RoomEntity.builder()
                .number(roomDto.number())
                .type(roomType)
                .build();

        newRoom = roomRepo.save(newRoom);
        return newRoom.getId();
    }

    public RoomDto read(@NotNull UUID roomId){
        RoomEntity room = roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(roomId,""));

        return RoomDto.builder()
                .id(room.getId())
                .typeId(room.getType().getId())
                .number(room.getNumber())
                .build();
    }

    public RoomEntity roomDtoToEntity(RoomDto roomToConvert){
        return null;
    }

    public List<RoomDto> readAllByHotel(@NotNull UUID hotelId){
        HotelEntity hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new EntityNotFoundException(hotelId,""));
        List<RoomEntity> roomsByHotel = roomRepo.findAllByType_Hotel(hotel);

        return getRoomDtos(roomsByHotel);
    }

    public List<RoomDto> readAllByType(@NotNull UUID typeId){
        RoomTypeEntity type = typeRepo.findById(typeId).orElseThrow(() -> new EntityNotFoundException(typeId,""));
        List<RoomEntity> roomsByHotelAndType = roomRepo.findAllByType(type);

        return getRoomDtos(roomsByHotelAndType);
    }

    public void update(@NotNull UUID roomId, @NotNull RoomDto roomDto) {

        RoomTypeEntity roomType = typeRepo.findById(roomDto.typeId()).orElseThrow(() -> new EntityNotFoundException(roomDto.typeId(),""));

        var updatedRoom = roomRepo.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(roomId,""));
        updatedRoom.setNumber(roomDto.number());
        updatedRoom.setType(roomType);
        roomRepo.save(updatedRoom);
    }

    public void delete(@NotNull UUID roomId){
        if(!roomRepo.existsById(roomId))
            throw new EntityNotFoundException(roomId,"");

        roomRepo.deleteById(roomId);
    }

    private List<RoomDto> getRoomDtos(List<RoomEntity> rooms) {
        return rooms.stream().map(room ->
                RoomDto.builder()
                .id(room.getId())
                .typeId(room.getType().getId())
                .number(room.getNumber())
                .build()
                ).toList();
    }


}
