package org.ukma.spring.crooodle.hotelsvc.api;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.internal.HotelRepo;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HotelSvc {
    private final HotelRepo repo;

    UUID create(@NotNull HotelUpsertDto upsertDto) {
        var entity = HotelEntity.builder()
                .name(upsertDto.name())
                .address(upsertDto.address())
                .build();
        entity = repo.save(entity);
        return entity.getId();
    }

    HotelDto read(@NotNull UUID id) {
        var entity = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
        return HotelDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .build();
    }

    void update(@NotNull UUID id, @NotNull HotelUpsertDto upsertDto) {
        var entity = repo.findById(id)
                .orElseThrow();
        entity.setName(upsertDto.name());
        entity.setAddress(upsertDto.address());
        repo.save(entity);
    }

    void delete(@NotNull UUID id) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException("Hotel not found");

        repo.deleteById(id);
    }
}
