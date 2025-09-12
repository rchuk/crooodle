package org.ukma.spring.crooodle.reservationsvc;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ReservationController {
    private final ReservationSvc resSvc;

    @PreAuthorize("hasRole('TRAVELER')")
    @PostMapping("/room/{roomId}/reservation")
    public UUID create(@PathVariable UUID roomId, @Valid @RequestBody ReservationCreateDto requestDto) {
        return resSvc.create(roomId, requestDto);
    }

    @PreAuthorize("hasRole('TRAVELER') || hasRole('HOTEL_OWNER')")
    @GetMapping("/reservation/{id}")
    public ReservationDetailedResponseDto read(@PathVariable UUID id) {
        return resSvc.read(id);
    }

    /* TODO
    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @GetMapping("/hotel/{hotelId}/reservation")
    public List<ReservationResponseDto> readAllByHotel(@PathVariable UUID hotelId, @RequestBody ReservationCriteriaDto requestDto) {
        return resSvc.readAllByHotel(hotelId, requestDto);
    }
    */

    @GetMapping("/room/{roomId}/reservation")
    public List<ReservationResponseDto> readAllByRoom(@PathVariable UUID roomId, @RequestBody(required = false) ReservationCriteriaDto requestDto) {
        return resSvc.readAllByRoom(roomId, requestDto);
    }

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PostMapping("/reservation/{id}/confirm")
    public void confirm(@PathVariable UUID id) {
        resSvc.confirm(id);
    }

    @PreAuthorize("hasRole('TRAVELER') || hasRole('HOTEL_OWNER')")
    @PostMapping("/reservation/{id}/cancel")
    public void cancel(@PathVariable UUID id) {
        resSvc.cancel(id);
    }
}
