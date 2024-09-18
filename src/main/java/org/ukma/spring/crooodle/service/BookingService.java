package org.ukma.spring.crooodle.service;

import org.ukma.spring.crooodle.model.Booking;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.model.Room;

import java.time.LocalDate;

public interface BookingService {
    Booking bookRoom(User user, Room room, LocalDate startDate, LocalDate endDate);
    boolean checkAvailability(Room room, LocalDate startDate, LocalDate endDate);
}
