package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.ukma.spring.crooodle.config.BookingConfig;
import org.ukma.spring.crooodle.exception.PublicBadRequestException;
import org.ukma.spring.crooodle.dto.BookingDto;
import org.ukma.spring.crooodle.model.Booking;
import org.ukma.spring.crooodle.model.Room;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.service.BookingService;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.service.RoomService;
import org.ukma.spring.crooodle.service.UserService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final RoomService roomService;

    @Autowired
    private UserService userService;

    // Injecting the new BookingConfig class
    @Autowired
    private BookingConfig bookingConfig;

    private List<Booking> existingBookings = new ArrayList<>();

    @Override
    public BookingDto bookRoom(long roomId, BookingDto bookingDto) {

        // Check if booking functionality is enabled
        if (!bookingConfig.isEnabled()) {
            throw new PublicBadRequestException("Booking functionality is currently disabled.");
        }

        // Unpack DTO
        var user = userService.getCurrentUser();
        var startDate = bookingDto.getStartDate();
        var endDate = bookingDto.getEndDate();
        var room = roomService.getRoom(roomId);

        // Validate booking duration
        long bookingDuration = ChronoUnit.DAYS.between(startDate, endDate);
        if (bookingDuration > bookingConfig.getMaxDays() || bookingDuration < bookingConfig.getMinDays()) {
            throw new PublicBadRequestException(
                    "Booking duration should be between " + bookingConfig.getMinDays() + " and " + bookingConfig.getMaxDays() + " days.");
        }

        // Check if the room is available
        if (checkAvailabilityImpl(room, startDate, endDate)) {
            Booking booking = Booking.builder()
                    .user(user)
                    .room(room)
                    .startDate(startDate)
                    .endDate(endDate)
                    .totalPrice(calculateTotalPrice(room, startDate, endDate))
                    .status("Confirmed")
                    .build();
            existingBookings.add(booking); // Add the new booking to the list
            return new BookingDto(booking);
        } else {
            throw new PublicBadRequestException("Date is already occupied.");
        }
    }

    @Override
    public Booking bookRoom(User user, long roomId, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public boolean checkAvailability(long roomId, LocalDate startDate, LocalDate endDate) {
        var room = roomService.getRoom(roomId);
        return checkAvailabilityImpl(room, startDate, endDate);
    }

    private boolean checkAvailabilityImpl(Room room, LocalDate startDate, LocalDate endDate) {
        for (Booking booking : existingBookings) {
            if (booking.getRoom().equals(room) &&
                    ((startDate.isBefore(booking.getEndDate()) && startDate.isAfter(booking.getStartDate())) ||
                            (endDate.isBefore(booking.getEndDate()) && endDate.isAfter(booking.getStartDate())) ||
                            startDate.equals(booking.getStartDate()) || endDate.equals(booking.getEndDate()))) {
                return false; // If dates overlap, room is not available
            }
        }
        return true; // If no conflicts, room is available
    }

    private int calculateTotalPrice(Room room, LocalDate startDate, LocalDate endDate) {
        var days = (int) ChronoUnit.DAYS.between(startDate, endDate);
        return days * room.getPricePerNight(); // Total price depends on the number of days and room price per night
    }
}
