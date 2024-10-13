package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.dto.BookingDto;

import java.time.LocalDate;

public interface BookingService {
    BookingDto bookRoom(long roomId, BookingDto bookingDto);
    boolean checkAvailability(long roomId, LocalDate startDate, LocalDate endDate);
}
