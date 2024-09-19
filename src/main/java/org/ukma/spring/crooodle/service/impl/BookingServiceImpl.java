package org.ukma.spring.crooodle.service.impl;

import org.ukma.spring.crooodle.model.Booking;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private List<Booking> existingBookings = new ArrayList<>(); // Список для фіктивних бронювань

    @Override
    public Booking bookRoom(User user, Room room, LocalDate startDate, LocalDate endDate) {
        if (checkAvailability(room, startDate, endDate)) {
            Booking booking = new Booking(user, room, startDate, endDate, calculateTotalPrice(room, startDate, endDate), "Confirmed");
            existingBookings.add(booking); // Додаємо нове бронювання до списку
            return booking;
        } else {
            throw new RuntimeException("Room is not available for the selected dates.");
        }
    }

    @Override
    public boolean checkAvailability(Room room, LocalDate startDate, LocalDate endDate) {
        for (Booking booking : existingBookings) {
            if (booking.getRoom().equals(room) &&
                    ((startDate.isBefore(booking.getEndDate()) && startDate.isAfter(booking.getStartDate())) ||
                            (endDate.isBefore(booking.getEndDate()) && endDate.isAfter(booking.getStartDate())) ||
                            startDate.equals(booking.getStartDate()) || endDate.equals(booking.getEndDate()))) {
                return false; // Якщо дати перетинаються, номер недоступний
            }
        }
        return true; // Якщо жодних конфліктів, номер доступний
    }

    private double calculateTotalPrice(Room room, LocalDate startDate, LocalDate endDate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        return days * room.getPricePerNight(); // Загальна ціна залежить від кількості днів та ціни за ніч
    }
}
