package org.ukma.spring.crooodle.WelcomeServise;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.WelcomeServise.LimitedBookingService;
import org.ukma.spring.crooodle.dto.BookingDto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@ConditionalOnMissingBean(LimitedBookingService.class)
public class DefaultBookingService {

    private static final int MAX_BOOKING_DAYS = 30; // Максимальна тривалість бронювання
    private static final int MIN_BOOKING_DAYS = 1;  // Мінімальна тривалість бронювання

    public boolean isBookingAllowed(BookingDto bookingDto) {
        LocalDate today = LocalDate.now();
        long days = ChronoUnit.DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate());

        // Перевіряємо, чи бронювання не в минулому
        if (bookingDto.getStartDate().isBefore(today)) {
            return false; // Забороняємо бронювання на минулі дати
        }

        // Перевіряємо, чи дата закінчення після дати початку
        if (days < MIN_BOOKING_DAYS || days > MAX_BOOKING_DAYS) {
            return false; // Забороняємо, якщо бронювання менше 1 дня або більше 30 днів
        }

        // Логіка дозволяє бронювання, якщо всі умови пройдені
        return true;
    }
}
