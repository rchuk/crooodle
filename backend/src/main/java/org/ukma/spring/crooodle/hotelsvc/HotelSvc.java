package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;
import org.ukma.spring.crooodle.usersvc.Role;
import org.ukma.spring.crooodle.usersvc.UserSvc;
import org.ukma.spring.crooodle.utils.exceptions.EntityNotFoundException;
import org.ukma.spring.crooodle.utils.exceptions.ForbiddenException;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HotelSvc {
    private final UserSvc userSvc;
    private final HotelRepo repo;

    public UUID create(@NotNull HotelUpsertDto upsertDto) {
        if (!canCreate(upsertDto))
            throw new ForbiddenException("Only hotel owners can create hotels");

        var entity = HotelEntity.builder()
            .name(upsertDto.name())
            .address(upsertDto.address())
            .ownerId(userSvc.getCurrentUser().id())
            .build();
        entity = repo.save(entity);

        return entity.getId();
    }

    public HotelDto read(@NotNull UUID id) {
        var entity = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Hotel"));
        if (!canRead(entity))
            throw new ForbiddenException("Cannot read hotel");

        return HotelDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .address(entity.getAddress())
            .build();
    }

    public void update(@NotNull UUID id, @NotNull HotelUpsertDto upsertDto) {
        var entity = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Hotel"));
        if (!canUpdate(entity, upsertDto))
            throw new ForbiddenException("Cannot update hotel");

        entity.setName(upsertDto.name());
        entity.setAddress(upsertDto.address());
        repo.save(entity);
    }

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
