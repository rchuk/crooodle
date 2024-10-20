package org.ukma.spring.crooodle.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.WelcomeServise.ExternalRoomAvailabilityService;
import org.ukma.spring.crooodle.dto.BookingDto;

import java.time.LocalDate;

@RestController
@Slf4j
public class ExternalRoomAvailabilityController {

    @Autowired(required = false)
    private ExternalRoomAvailabilityService externalRoomAvailabilityService;

    @GetMapping("/check-room-availability")
    public String checkRoomAvailability(
            @RequestParam Long roomId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        log.info("Received request to check room availability for room {}", roomId);
        if (externalRoomAvailabilityService != null) {
            BookingDto bookingDto = new BookingDto();
            bookingDto.setRoomId(roomId);
            bookingDto.setStartDate(LocalDate.parse(startDate));
            bookingDto.setEndDate(LocalDate.parse(endDate));

            boolean isAvailable = externalRoomAvailabilityService.isRoomAvailable(bookingDto);

            if (isAvailable) {
                return "Room " + roomId + " is available for booking.";
            } else {
                return "Room " + roomId + " is not available for booking.";
            }
        } else {
            return "External room availability service is not available.";
        }
    }
}
