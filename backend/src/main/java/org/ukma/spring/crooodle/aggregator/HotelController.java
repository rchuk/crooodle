package org.ukma.spring.crooodle.aggregator;

import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public UUID create(@RequestBody HotelUpsertDto hotelUpsertDto) {
        return svc.create(hotelUpsertDto);
    }

    @GetMapping("/{id}")
    public HotelDto read(@PathVariable UUID id) {
        return svc.read(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody HotelUpsertDto hotelUpsertDto) {
        svc.update(id, hotelUpsertDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        svc.delete(id);
    }
}
