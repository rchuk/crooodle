package org.ukma.spring.crooodle.reservationsvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dtos.PageDto;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationCreateDto;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationCriteria;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationDto;
import org.ukma.spring.crooodle.reservationsvc.service.ReservationService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/room/{roomId}/reserve")
    public UUID create(@PathVariable UUID roomId, @RequestBody ReservationCreateDto reservationCreateDto) {
        return reservationService.create(roomId, reservationCreateDto);
    }

    @GetMapping("/reservation/{id}")
    public ReservationDto read(@PathVariable UUID id) {
        return reservationService.get(id);
    }

		@GetMapping("/reservations")
		public PageDto<ReservationDto> getMyReservations(@RequestParam(required = false) ReservationCriteria criteria, @PageableDefault(sort = "checkInDate") Pageable pageable) {
			return PageDto.of(reservationService.getMyReservations(criteria, pageable));
		}

    @GetMapping("/hotel/{hotelId}/reservations")
    public PageDto<ReservationDto> readAllByHotel(@PathVariable UUID hotelId, @RequestParam(required = false) ReservationCriteria criteria, @PageableDefault(sort = "checkInDate") Pageable pageable) {
        return PageDto.of(reservationService.getHotelReservations(hotelId, criteria, pageable));
    }

    @GetMapping("/room/{roomId}/reservations")
    public PageDto<ReservationDto> readAllByRoom(@PathVariable UUID roomId, @RequestParam(required = false) ReservationCriteria criteria, @PageableDefault(sort = "checkInDate") Pageable pageable) {
        return PageDto.of(reservationService.getRoomReservations(roomId, criteria, pageable));
    }
}
