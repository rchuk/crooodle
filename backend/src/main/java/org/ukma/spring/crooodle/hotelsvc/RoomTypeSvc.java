package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomTypeResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomTypeUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomTypeEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomTypeRepo;
import org.ukma.spring.crooodle.usersvc.Role;
import org.ukma.spring.crooodle.usersvc.UserSvc;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;
import org.ukma.spring.crooodle.utils.exceptions.ForbiddenException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomTypeSvc {
    private final UserSvc userSvc;

    private final RoomTypeRepo typeRepo;
    private final HotelRepo hotelRepo;

    public UUID create(UUID hotelId, @NotNull RoomTypeUpsertDto requestDto) {
        if (!canCreate(hotelId))
            throw new ForbiddenException("Cannot create Hotel");

        var hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new EntityNotFoundException(hotelId, "Hotel"));
        var roomType = RoomTypeEntity.builder()
            .hotel(hotel)
            .name(requestDto.name())
            .price(requestDto.price())
            .build();
        roomType = typeRepo.save(roomType);

        return roomType.getId();
    }

    public RoomTypeResponseDto read(@NotNull UUID id) {
        var roomType = typeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Room Type"));

        return roomTypeEntityToDto(roomType);
    }

    public List<RoomTypeResponseDto> readAllByHotel(@NotNull UUID hotelId) {
        var hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new EntityNotFoundException(hotelId, "Hotel"));

        return typeRepo.findAllByHotel(hotel).stream()
            .map(this::roomTypeEntityToDto)
            .toList();
    }

    public void update(@NotNull UUID roomTypeId, @NotNull RoomTypeResponseDto roomTypeDto) {
        var roomType = typeRepo.findById(roomTypeId).orElseThrow(() -> new EntityNotFoundException(roomTypeId, "Room Type"));
        if (!canUpdate(roomType))
            throw new ForbiddenException("Cannot update Room Type");

        roomType.setName(roomTypeDto.name());
        roomType.setPrice(roomTypeDto.price());
        typeRepo.save(roomType);
    }

    public void delete(@NotNull UUID roomTypeId) {
        var roomType = typeRepo.findById(roomTypeId).orElseThrow(() -> new EntityNotFoundException(roomTypeId, "Room Type"));
        if (!canDelete(roomType))
            throw new ForbiddenException("Cannot delete Hotel");

        typeRepo.deleteById(roomTypeId);
    }

    public RoomTypeResponseDto roomTypeEntityToDto(RoomTypeEntity roomType) {
        return RoomTypeResponseDto.builder()
            .id(roomType.getId())
            .name(roomType.getName())
            .hotelId(roomType.getHotel().getId())
            .price(roomType.getPrice())
            .build();
    }

    private boolean canCreate(UUID hotelId) {
        return userSvc.getCurrentUserRole().equals(Role.ROLE_HOTEL_OWNER);
    }

    private boolean canUpdate(RoomTypeEntity roomType) {
        return userSvc.getCurrentUser().id().equals(roomType.getHotel().getOwnerId());
    }

    private boolean canDelete(RoomTypeEntity roomType) {
        return userSvc.getCurrentUser().id().equals(roomType.getHotel().getOwnerId());
    }
}

