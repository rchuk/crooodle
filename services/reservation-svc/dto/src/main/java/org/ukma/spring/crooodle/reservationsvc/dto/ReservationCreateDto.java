package org.ukma.spring.crooodle.reservationsvc.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ReservationCreateDto(
    @NotNull
    Date checkInDate,
    @NotNull
    Date checkOutDate
) {

}
