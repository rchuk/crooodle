package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.model.Booking;
import org.ukma.spring.crooodle.model.User;

import java.time.LocalDate;

public interface BookingService {
    BookingDto bookRoom(long roomId, BookingDto bookingDto);

    Booking bookRoom(User user, long roomId, LocalDate startDate, LocalDate endDate);
    boolean checkAvailability(long roomId, LocalDate startDate, LocalDate endDate);
}
