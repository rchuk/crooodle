package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomRepo;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomTypeRepo;
import org.ukma.spring.crooodle.usersvc.Role;
import org.ukma.spring.crooodle.usersvc.UserSvc;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;
import org.ukma.spring.crooodle.utils.exceptions.ForbiddenException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomSvc {
    private final UserSvc userSvc;

    private final RoomTypeSvc roomTypeSvc;

    private final RoomTypeRepo typeRepo;
    private final HotelRepo hotelRepo;
    private final RoomRepo roomRepo;

    public UUID create(UUID hotelId, RoomUpsertDto requestDto) {
        if (!canCreate(hotelId))
            throw new ForbiddenException("Can't create hotel");

        var roomType = typeRepo.findById(requestDto.roomTypeId()).orElseThrow(() -> new EntityNotFoundException(requestDto.roomTypeId(), "Room Type"));
        var room = RoomEntity.builder()
            .name(requestDto.name())
            .type(roomType)
            .build();
        room = roomRepo.save(room);

        return room.getId();
    }

    public RoomResponseDto read(@NotNull UUID roomId) {
        var room = roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(roomId, "Room"));

        return RoomResponseDto.builder()
            .id(room.getId())
            .type(roomTypeSvc.roomTypeEntityToDto(room.getType()))
            .name(room.getName())
            .build();
    }

    public List<RoomResponseDto> readAllByHotel(@NotNull UUID hotelId) {
        var hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new EntityNotFoundException(hotelId, "Hotel"));

        return roomRepo.findAllByType_Hotel(hotel)
            .stream()
            .map(this::roomEntityToDto)
            .toList();
    }

    public List<RoomResponseDto> readAllByType(@NotNull UUID typeId) {
        var type = typeRepo.findById(typeId).orElseThrow(() -> new EntityNotFoundException(typeId, "Room Type"));

        return roomRepo.findAllByType(type)
            .stream()
            .map(this::roomEntityToDto)
            .toList();
    }

    public void update(@NotNull UUID roomId, @NotNull RoomResponseDto roomDto) {
        var roomType = typeRepo.findById(roomDto.type().id()).orElseThrow(() -> new EntityNotFoundException(roomDto.type().id(), "Room Type"));
        var room = roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(roomId, "Room"));
        if (!canUpdate(room))
            throw new ForbiddenException("Can't update room");

        room.setName(roomDto.name());
        room.setType(roomType);
        roomRepo.save(room);
    }

    public void delete(@NotNull UUID roomId) {
        var room = roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(roomId, "Room"));
        if (!canDelete(room))
            throw new ForbiddenException("Can't delete room");

        roomRepo.deleteById(roomId);
    }

    public RoomResponseDto roomEntityToDto(RoomEntity entity) {
        return RoomResponseDto.builder()
            .id(entity.getId())
            .type(roomTypeSvc.roomTypeEntityToDto(entity.getType()))
            .name(entity.getName())
            .build();
    }

    private boolean canCreate(UUID hotelId) {
        return userSvc.getCurrentUserRole().equals(Role.ROLE_HOTEL_OWNER);
    }

    private boolean canUpdate(RoomEntity room) {
        return userSvc.getCurrentUser().id().equals(room.getType().getHotel().getOwnerId());
    }

    private boolean canDelete(RoomEntity room) {
        return userSvc.getCurrentUser().id().equals(room.getType().getHotel().getOwnerId());
    }
}
