package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.ukma.spring.crooodle.exception.PublicBadRequestException;
import org.ukma.spring.crooodle.dto.BookingDto;
import org.ukma.spring.crooodle.model.Booking;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.service.BookingService;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.service.RoomService;
import org.ukma.spring.crooodle.service.UserExpService;
import org.ukma.spring.crooodle.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final RoomService roomService;

    @Autowired
    private UserService userService;

    private List<Booking> existingBookings = new ArrayList<>(); // Список для фіктивних бронювань

    @Override
    public BookingDto bookRoom(long roomId, BookingDto bookingDto) {

        //unpack
        var user = userService.getCurrentUser();
        var startDate = bookingDto.getStartDate();
        var endDate = bookingDto.getEndDate();

        var room = roomService.getRoom(roomId);

        if (checkAvailabilityImpl(room, startDate, endDate)) {
            Booking booking = Booking.builder()
                .user(user)
                .room(room)
                .startDate(startDate)
                .endDate(endDate)
                .totalPrice(calculateTotalPrice(room, startDate, endDate))
                .status("Confirmed")
                .build();
            existingBookings.add(booking); // Додаємо нове бронювання до списку
            return new BookingDto(booking);
        } else {
            throw new PublicBadRequestException("Date is already occupied");
        }
    }

    @Override
    public boolean checkAvailability(long roomId, LocalDate startDate, LocalDate endDate) {
        var room = roomService.getRoom(roomId);

        return checkAvailabilityImpl(room, startDate, endDate);
    }

    private boolean checkAvailabilityImpl(Room room, LocalDate startDate, LocalDate endDate) {
        // TODO: Validate that room is from current hotel
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

    private int calculateTotalPrice(Room room, LocalDate startDate, LocalDate endDate) {
        var days = (int)java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);

        return days * room.getPricePerNight(); // Загальна ціна залежить від кількості днів та ціни за ніч
    }
}
