package org.ukma.spring.crooodle.hotelsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomController {
    private final RoomSvc roomSvc;

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PostMapping
    public UUID create(@RequestBody RoomDto roomDto) {
        return roomSvc.create(roomDto);
    }

    @GetMapping("/{id}")
    public RoomDto read(@PathVariable UUID id) {
        return roomSvc.read(id);
    }

    @GetMapping("/rooms/{id}")
    public List<RoomDto> readAllByHotel(@PathVariable UUID id) {
        return roomSvc.readAllByHotel(id);
    }

    @GetMapping("/rooms")
    public List<RoomDto> readAllByType(@RequestParam UUID roomId) {
        return roomSvc.readAllByType(roomId);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody RoomDto roomDtoToUpdate) {
        roomSvc.update(id, roomDtoToUpdate);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        roomSvc.delete(id);
    }
}

