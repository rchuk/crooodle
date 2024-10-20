package org.ukma.spring.crooodle.dto;

import lombok.Getter;
import lombok.Setter;
import org.ukma.spring.crooodle.model.Booking;
import java.time.LocalDate;

@Getter
@Setter  // Додаємо аннотацію для генерування сеттерів
public class BookingDto {

    private Long roomId;
    private LocalDate startDate;
    private LocalDate endDate;

    // Конструктор без параметрів
    public BookingDto() {
    }

    // Конструктор для створення DTO на основі об'єкта Booking
    public BookingDto(Booking booking) {
        this.roomId = booking.getRoom().getId();
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
    }
}
