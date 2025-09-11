package org.ukma.spring.crooodle.reservationsvc;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public record ReservationCreateDto(
    @NotNull
    UUID roomId,
    @NotNull
    Date checkInDate,
    @NotNull
    Date checkOutDate
) {

}
