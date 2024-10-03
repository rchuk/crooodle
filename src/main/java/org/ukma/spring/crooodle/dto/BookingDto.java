package org.ukma.spring.crooodle.dto;

import lombok.Getter;
import org.ukma.spring.crooodle.model.Booking;
import org.ukma.spring.crooodle.model.User;

import java.time.LocalDate;

@Getter
public class BookingDto {

    long roomId;
    LocalDate startDate;
    LocalDate endDate;
    public BookingDto(Booking booking) {
        //TODO: implement code
    }


}
