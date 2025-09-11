package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomTypeResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomTypeUpsertDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class RoomTypeController {
    private final RoomTypeSvc typeSvc;

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PostMapping("/hotel/{hotelId}/room-type")
    public UUID create(@PathVariable UUID hotelId, @Valid @RequestBody RoomTypeUpsertDto requestDto) {
        return typeSvc.create(hotelId, requestDto);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @GetMapping("/room-type/{id}")
    public RoomTypeResponseDto read(@PathVariable UUID id) {
        return typeSvc.read(id);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @GetMapping("/hotel/{hotelId}/room-type")
    public List<RoomTypeResponseDto> readAllByHotel(@PathVariable UUID hotelId) {
        return typeSvc.readAllByHotel(hotelId);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PutMapping("/room-type/{id}")
    public void update(@PathVariable UUID id, @Valid @RequestBody RoomTypeResponseDto requestDto) {
        typeSvc.update(id, requestDto);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @DeleteMapping("/room-type/{id}")
    public void delete(@PathVariable UUID id) {
        typeSvc.delete(id);
    }
}
