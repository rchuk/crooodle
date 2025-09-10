package org.ukma.spring.crooodle.reservationsvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.reservationsvc.ReservationDto;
import org.ukma.spring.crooodle.reservationsvc.ReservationSvc;
import org.ukma.spring.crooodle.roomsvc.RoomSvc;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/reservation")
@RestController
public class ReservationController {

    private final ReservationSvc resSvc;


    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @PostMapping
    public UUID create(@RequestBody ReservationDto newReservation){

        return resSvc.create(newReservation);
    }

    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @GetMapping("/{id}")
    public ReservationDto read(@PathVariable UUID id){
        return resSvc.read(id);
    }

    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @GetMapping("/reservations-hotel")
    public List<ReservationDto> readAllByHotel(@RequestParam UUID hotelId, @RequestParam UUID userId){
        return resSvc.readAllByHotel(hotelId, userId);
    }

    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @GetMapping("/reservations-dates")
    public List<ReservationDto> readAllByDates(@RequestParam UUID userId, @RequestParam Date checkIn, @RequestParam Date checkOut){
        return resSvc.readAllByDates(userId, checkIn, checkOut);
    }

    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @GetMapping("/states")
    public List<ReservationDto> readAllByState(@RequestParam UUID userId, @RequestParam String state){
        return resSvc.readAllByState(userId, state);
    }

    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody ReservationDto resDtoToUpdate){
        resSvc.update(id, resDtoToUpdate);
    }

    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @PutMapping("confirm/{id}")
    public void confirm(@PathVariable UUID id){
        resSvc.confirm(id);
    }

    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @PutMapping("cancel/{id}")
    public void cancel(@PathVariable UUID id){
        resSvc.cancel(id);
    }

    @PreAuthorize("hasRole('ROLE_TRAVELER')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id){
        resSvc.delete(id);
    }
}
