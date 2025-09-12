package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;
import org.ukma.spring.crooodle.hotelsvc.internal.RoomRepo;
import org.ukma.spring.crooodle.usersvc.Role;
import org.ukma.spring.crooodle.usersvc.UserSvc;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;
import org.ukma.spring.crooodle.utils.exceptions.ForbiddenException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HotelSvc {
    private final UserSvc userSvc;
    private final HotelRepo repo;
    private final RoomRepo roomRepo;

    public UUID create(@NotNull HotelUpsertDto upsertDto) {
        if (!canCreate(upsertDto))
            throw new ForbiddenException("Only hotel owners can create hotels");

        var entity = HotelEntity.builder()
            .name(upsertDto.name())
            .address(upsertDto.address())
            .ownerId(userSvc.getCurrentUser().id())
            .build();
        entity = repo.saveAndFlush(entity);

        return entity.getId();
    }

    public HotelResponseDto read(@NotNull UUID id) {
        return hotelEntityToDto(get(id));
    }

    HotelResponseDto hotelEntityToDto(HotelEntity hotel) {
        return HotelResponseDto.builder()
            .id(hotel.getId())
            .name(hotel.getName())
            .address(hotel.getAddress())
            .ownerId(hotel.getOwnerId())
            .roomCount(roomRepo.countAllByType_Hotel(hotel))
            .build();
    }

    HotelEntity get(@NotNull UUID id) {
        var hotel = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Hotel"));
        if (!canRead(hotel))
            throw new ForbiddenException("Cannot read hotel");

        return hotel;
    }

    @Transactional
    public void update(@NotNull UUID id, @NotNull HotelUpsertDto upsertDto) {
        var entity = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Hotel"));
        if (!canUpdate(entity, upsertDto))
            throw new ForbiddenException("Cannot update hotel");

        entity.setName(upsertDto.name());
        entity.setAddress(upsertDto.address());
        repo.saveAndFlush(entity);
    }

    public List<HotelResponseDto> readAll() {
        return repo.findAll().stream()
            .map(this::hotelEntityToDto)
            .toList();
    }

    @Transactional
    public void delete(@NotNull UUID id) {
        var hotel = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Hotel"));
        if (!canDelete(hotel))
            throw new ForbiddenException("Cannot delete Hotel");

        repo.deleteById(id);
    }

    private boolean canCreate(HotelUpsertDto ignored_upsertDto) {
        return userSvc.getCurrentUserRole().equals(Role.ROLE_HOTEL_OWNER);
    }

    private boolean canUpdate(HotelEntity hotel, HotelUpsertDto ignored_upsertDto) {
        if (!userSvc.getCurrentUserRole().equals(Role.ROLE_HOTEL_OWNER))
            return false;

        return hotel.getOwnerId().equals(userSvc.getCurrentUser().id());
    }

    private boolean canRead(HotelEntity ignored_hotel) {
        return true;
    }

    private boolean canDelete(HotelEntity hotel) {
        if (!userSvc.getCurrentUserRole().equals(Role.ROLE_HOTEL_OWNER))
            return false;

        return hotel.getOwnerId().equals(userSvc.getCurrentUser().id());
    }
}
