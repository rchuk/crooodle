package org.ukma.spring.crooodle.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ukma.spring.crooodle.WelcomeServise.DefaultBookingService;
import org.ukma.spring.crooodle.dto.BookingDto;

@RestController
@Slf4j
public class DefaultBookingController {

    @Autowired(required = false)
    private DefaultBookingService defaultBookingService;

    @GetMapping("/default-welcome")
    public String getDefaultWelcomeMessage(@RequestParam String name, @RequestParam Long roomId) {
        log.info("Received request for default welcome message for user {}", name);
        if (defaultBookingService != null) {
            // Тепер ми можемо використовувати конструктор без параметрів і методи setRoomId
            BookingDto bookingDto = new BookingDto();
            bookingDto.setRoomId(roomId);  // Встановлюємо roomId
            // Додайте також startDate та endDate за необхідністю

            boolean isAllowed = defaultBookingService.isBookingAllowed(bookingDto);

            if (isAllowed) {
                return "Welcome, " + name + "! Your booking is confirmed.";
            } else {
                return "Sorry, " + name + ". Booking is not allowed.";
            }
        } else {
            return "No default booking service available.";
        }
    }
}
