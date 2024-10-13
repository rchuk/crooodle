package org.ukma.spring.crooodle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.WelcomeServise.LimitedBookingService;
import org.ukma.spring.crooodle.dto.BookingDto;

import java.time.LocalDate;

@RestController
public class LimitedBookingController {

    @Autowired(required = false)
    private LimitedBookingService limitedBookingService;

    @GetMapping("/check-booking-limit")
    public String checkBookingLimit(
            @RequestParam Long roomId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        if (limitedBookingService != null) {
            BookingDto bookingDto = new BookingDto();
            bookingDto.setRoomId(roomId);
            bookingDto.setStartDate(LocalDate.parse(startDate));
            bookingDto.setEndDate(LocalDate.parse(endDate));

            boolean isAllowed = limitedBookingService.isBookingAllowed(bookingDto);

            if (isAllowed) {
                return "Booking is allowed.";
            } else {
                return "Booking exceeds the maximum allowed days.";
            }
        } else {
            return "Limited booking service is not available.";
        }
    }
}