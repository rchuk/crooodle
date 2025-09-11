package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomUpsertDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class RoomController {
    private final RoomSvc roomSvc;

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PostMapping("/hotel/{hotelId}/room")
    public UUID create(UUID hotelId, @Valid @RequestBody RoomUpsertDto requestDto) {
        return roomSvc.create(hotelId, requestDto);
    }

    @GetMapping("/room/{id}")
    public RoomResponseDto read(@PathVariable UUID id) {
        return roomSvc.read(id);
    }

    @GetMapping("/hotel/{hotelId}/room")
    public List<RoomResponseDto> readAllByHotel(@PathVariable UUID hotelId) {
        return roomSvc.readAllByHotel(hotelId);
    }

    @GetMapping("/room-type/{roomTypeId}/room")
    public List<RoomResponseDto> readAllByType(@RequestParam UUID roomTypeId) {
        return roomSvc.readAllByType(roomTypeId);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PutMapping("/room/{id}")
    public void update(@PathVariable UUID id, @RequestBody RoomResponseDto roomDtoToUpdate) {
        roomSvc.update(id, roomDtoToUpdate);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @DeleteMapping("/room/{id}")
    public void delete(@PathVariable UUID id) {
        roomSvc.delete(id);
    }
}

