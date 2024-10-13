package org.ukma.spring.crooodle.WelcomeServise;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.BookingDto;

@Service
@ConditionalOnProperty(name = "booking.limit.enabled", havingValue = "true", matchIfMissing = true)
public class LimitedBookingService {

    private static final int MAX_BOOKING_DAYS = 30;

    public boolean isBookingAllowed(BookingDto bookingDto) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate());
        return days <= MAX_BOOKING_DAYS;
    }
}
