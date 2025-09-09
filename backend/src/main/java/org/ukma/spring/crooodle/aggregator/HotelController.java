package org.ukma.spring.crooodle.aggregator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.hotelsvc.HotelDto;
import org.ukma.spring.crooodle.hotelsvc.HotelSvc;
import org.ukma.spring.crooodle.hotelsvc.HotelUpsertDto;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/hotel")
@RestController
public class HotelController {
    private final HotelSvc svc;

    @PreAuthorize("hasRole('ROLE_HOTEL_OWNER')")
    @PostMapping
    public UUID create(@RequestBody HotelUpsertDto hotelUpsertDto) {
        return svc.create(hotelUpsertDto);
    }

    @GetMapping("/{id}")
    public HotelDto read(@PathVariable UUID id) {
        return svc.read(id);
    }

    @PreAuthorize("hasRole('ROLE_HOTEL_OWNER')")
    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody HotelUpsertDto hotelUpsertDto) {
        svc.update(id, hotelUpsertDto);
    }

    @PreAuthorize("hasRole('ROLE_HOTEL_OWNER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        svc.delete(id);
    }
}
