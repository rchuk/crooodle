package org.ukma.spring.crooodle.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.reservationsvc.ReservationDto;
import org.ukma.spring.crooodle.reservationsvc.ReservationSvc;
import org.ukma.spring.crooodle.roomsvc.RoomSvc;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/book")
@RestController
public class ReservationController {

    private final ReservationSvc roomSvc;


    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @PostMapping
    public UUID create(@RequestBody ReservationDto newReservation){

        return null;
    }
}
