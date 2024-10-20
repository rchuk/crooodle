package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.BookingDto;
import org.ukma.spring.crooodle.dto.RoomResponseDto;
import org.ukma.spring.crooodle.dto.RoomUpdateRequestDto;
import org.ukma.spring.crooodle.service.BookingService;
import org.ukma.spring.crooodle.service.RoomService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomsController {
    private final BookingService bookingService;
    private final RoomService roomService;

    // Load room
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public RoomResponseDto getRoom(@PathVariable long id) {
        return roomService.loadRoom(id);
    }

    // Book Room
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping("/{id}/book")
    public BookingDto book(@PathVariable long id, @RequestBody @Valid BookingDto bookingDto) {
        return bookingService.bookRoom(id, bookingDto);
    }

    @PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
    @PutMapping("/{id}")
    public void updateRoom(@PathVariable long id, @RequestBody @Valid RoomUpdateRequestDto requestDto) {
        roomService.updateRoom(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable long id) {
        roomService.deleteRoom(id);
    }
}
