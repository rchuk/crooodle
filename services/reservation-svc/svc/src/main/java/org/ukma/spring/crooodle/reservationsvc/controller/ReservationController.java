package org.ukma.spring.crooodle.reservationsvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationCriteria;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationDto;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationUpsertDto;
import org.ukma.spring.crooodle.reservationsvc.service.ReservationService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/room/{roomId}/reserve")
    public UUID create(@PathVariable UUID roomId, @RequestBody ReservationUpsertDto reservationUpsertDto) {
        return reservationService.create(roomId, reservationUpsertDto);
    }

    @GetMapping("/reservation/{id}")
    public ReservationDto read(@PathVariable UUID id) {
        return reservationService.read(id);
    }

    @GetMapping("/hotel/{hotelId}/reservations")
    public List<ReservationDto> readAllByHotel(@PathVariable UUID hotelId, @RequestBody(required = false)ReservationCriteria criteria) {
        return reservationService.readAllByHotel(roomId, criteria);
    }

    @GetMapping("/room/{roomId}/reservations")
    public List<ReservationDto> readAllByRoom(@PathVariable UUID roomId, @RequestBody(required = false)ReservationCriteria criteria) {
        return reservationService.readAllByRoom(roomId, criteria);
    }

    @DeleteMapping("/reservation/{id}")
    public void delete(@PathVariable UUID id) {
        return reservationService.delete(id);
    }
}
