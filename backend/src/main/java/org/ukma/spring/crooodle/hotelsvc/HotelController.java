package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelUpsertDto;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/hotel")
@RestController
public class HotelController {
    private final HotelSvc svc;

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PostMapping
    public UUID create(@Valid @RequestBody HotelUpsertDto hotelUpsertDto) {
        return svc.create(hotelUpsertDto);
    }

    @GetMapping("/{id}")
    public HotelResponseDto read(@PathVariable UUID id) {
        return svc.read(id);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @Valid @RequestBody HotelUpsertDto hotelUpsertDto) {
        svc.update(id, hotelUpsertDto);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        svc.delete(id);
    }
}
