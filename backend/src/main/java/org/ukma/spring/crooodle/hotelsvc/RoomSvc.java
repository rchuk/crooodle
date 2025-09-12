package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomRepo;
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
    private final HotelSvc hotelSvc;
    private final RoomRepo roomRepo;

    private final ApplicationEventPublisher eventPub;

    @Transactional
    public UUID create(UUID hotelId, RoomUpsertDto requestDto) {
        if (!canCreate(hotelId))
            throw new ForbiddenException("Can't create hotel");

        var roomType = roomTypeSvc.get(requestDto.roomTypeId());
        var room = RoomEntity.builder()
            .name(requestDto.name())
            .type(roomType)
            .build();
        room = roomRepo.saveAndFlush(room);

        return room.getId();
    }

    public RoomResponseDto read(@NotNull UUID roomId) {
        var room = get(roomId);

        return RoomResponseDto.builder()
            .id(room.getId())
            .type(roomTypeSvc.roomTypeEntityToDto(room.getType()))
            .name(room.getName())
            .build();
    }

    RoomEntity get(@NotNull UUID roomId) {
        return roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(roomId, "Room"));
    }

    public List<RoomResponseDto> readAllByHotel(@NotNull UUID hotelId) {
        // TODO: Add pagination
        var hotel = hotelSvc.get(hotelId);

        return roomRepo.findAllByType_Hotel(hotel)
            .stream()
            .map(this::roomEntityToDto)
            .toList();
    }

    public List<RoomResponseDto> readAllByType(@NotNull UUID typeId) {
        var type = roomTypeSvc.get(typeId);

        return roomRepo.findAllByType(type)
            .stream()
            .map(this::roomEntityToDto)
            .toList();
    }

    @Transactional
    public void update(@NotNull UUID roomId, @NotNull RoomResponseDto roomDto) {
        var roomType = roomTypeSvc.get(roomId);
        var room = get(roomId);
        if (!canUpdate(room))
            throw new ForbiddenException("Can't update room");

        room.setName(roomDto.name());
        room.setType(roomType);
        roomRepo.saveAndFlush(room);
    }

    @Transactional
    public void delete(@NotNull UUID roomId) {
        var room = get(roomId);
        if (!canDelete(room))
            throw new ForbiddenException("Can't delete room");

        roomRepo.deleteById(roomId);
        eventPub.publishEvent(RoomDeletedEvent.builder().roomId(roomId).build());
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
