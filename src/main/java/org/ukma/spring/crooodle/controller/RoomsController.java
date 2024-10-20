package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RoomsController {
    private final BookingService bookingService;
    private final RoomService roomService;

    // Load room
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public RoomResponseDto getRoom(@PathVariable long id) {
        log.info("Getting room with id {}", id);
        return roomService.loadRoom(id);
    }

    // Book Room
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping("/{id}/book")
    public BookingDto book(@PathVariable long id, @RequestBody @Valid BookingDto bookingDto) {
        log.info("Booking room with id {}", id);
        return bookingService.bookRoom(id, bookingDto);
    }

    @PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
    @PutMapping("/{id}")
    public void updateRoom(@PathVariable long id, @RequestBody @Valid RoomUpdateRequestDto requestDto) {
        log.info("Updating room with id {}", id);
        roomService.updateRoom(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_HOTEL_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable long id) {
        log.info("Deleting room with id {}", id);
        roomService.deleteRoom(id);
    }
}
