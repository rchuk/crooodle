package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;
import org.ukma.spring.crooodle.reservationsvc.ReservationState;
import org.ukma.spring.crooodle.reservationsvc.ReservationSvc;
import org.ukma.spring.crooodle.reservationsvc.internal.ReservationEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomRepo;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomTypeEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomTypeRepo;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomSvc {

    private final RoomTypeRepo typeRepo;
    private final HotelRepo hotelRepo;
    private final RoomRepo roomRepo;
    private final ReservationSvc resSvc;

    public UUID create(@NotNull RoomDto roomDto){

        RoomTypeEntity roomType = typeRepo.findById(roomDto.typeId()).orElseThrow(() -> new EntityNotFoundException(roomDto.typeId() ,""));
        HotelEntity hotel = hotelRepo.findById(roomDto.hotelId()).orElseThrow(() -> new EntityNotFoundException(roomDto.hotelId(),""));

        var newRoom = RoomEntity.builder()
                .number(roomDto.number())
                .type(roomType)
                .hotel(hotel)
                .build();

        newRoom = roomRepo.save(newRoom);
        return newRoom.getId();
    }

    public RoomDto read(@NotNull UUID roomId){

        Date now = new Date();
        boolean isOccupied = true;
        RoomEntity room = roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(roomId,""));

        // По ідеї на конкретну дату й годину може бути ОДНА бронь - переробити на ReservationEntity
        List<ReservationEntity> roomBooks = resSvc.getBooksByRoomAndStateAndCheckin(room, ReservationState.CONFIRMED, now);
        roomBooks.addAll(resSvc.getBooksByRoomAndStateAndCheckin(room, ReservationState.SETTLED, now));

        if(!roomBooks.isEmpty()) isOccupied = false;

        return RoomDto.builder()
                .id(room.getId())
                .typeId(room.getType().getId())
                .hotelId(room.getHotel().getId())
                .number(room.getNumber())
                .isOccupied(isOccupied)
                .build();
    }

    public RoomEntity roomDtoToEntity(RoomDto roomToConvert){
        return null;
    }

    public List<RoomDto> readAllByHotel(@NotNull UUID hotelId){

        Date now = new Date();
        HotelEntity hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new EntityNotFoundException(hotelId,""));
        List<RoomEntity> roomsByHotel = roomRepo.findAllByHotel(hotel);

        return getRoomDtos(now, roomsByHotel);
    }

    public List<RoomDto> readAllByHotelAndType(@NotNull UUID hotelId, @NotNull UUID typeId){

        Date now = new Date();
        HotelEntity hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new EntityNotFoundException(hotelId,""));
        RoomTypeEntity type = typeRepo.findById(typeId).orElseThrow(() -> new EntityNotFoundException(typeId,""));
        List<RoomEntity> roomsByHotelAndType = roomRepo.findAllByHotelAndType(hotel, type);

        return getRoomDtos(now, roomsByHotelAndType);
    }

    public void update(@NotNull UUID roomId, @NotNull RoomDto roomDto) {

        RoomTypeEntity roomType = typeRepo.findById(roomDto.typeId()).orElseThrow(() -> new EntityNotFoundException(roomDto.typeId(),""));
        HotelEntity hotel = hotelRepo.findById(roomDto.hotelId()).orElseThrow(() -> new EntityNotFoundException(roomDto.hotelId(),""));

        var updatedRoom = roomRepo.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(roomId,""));
        updatedRoom.setNumber(roomDto.number());
        updatedRoom.setType(roomType);
        updatedRoom.setHotel(hotel);
        roomRepo.save(updatedRoom);
    }

    public void delete(@NotNull UUID roomId){
        if(!roomRepo.existsById(roomId))
            throw new EntityNotFoundException(roomId,"");

        roomRepo.deleteById(roomId);
    }

    private List<RoomDto> getRoomDtos(Date currentDate, List<RoomEntity> rooms) {
        List<RoomDto> roomsDTO = new ArrayList<>();
        List<Boolean> roomOccupationStates = new ArrayList<>(); // for the current time


        for(RoomEntity re : rooms){
            if (resSvc.existsByRoomAndCheckinAndState(re, currentDate, ReservationState.CONFIRMED)
                    || resSvc.existsByRoomAndCheckinAndState(re, currentDate, ReservationState.SETTLED))
                roomOccupationStates.add(true);
            else roomOccupationStates.add(false);
        }

        for(int i=0; i<roomOccupationStates.size(); i++){
            roomsDTO.add(RoomDto.builder()
                    .id(rooms.get(i).getId())
                    .typeId(rooms.get(i).getType().getId())
                    .hotelId(rooms.get(i).getHotel().getId())
                    .number(rooms.get(i).getNumber())
                    .isOccupied(roomOccupationStates.get(i))
                    .build());
        }

        return roomsDTO;
    }


}
