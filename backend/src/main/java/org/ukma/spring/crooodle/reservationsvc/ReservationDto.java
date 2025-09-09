package org.ukma.spring.crooodle.reservationsvc;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ReservationDto(
        @NotNull
        UUID id,
        @Null
        UUID roomId,
        @NotNull
        int price,
        @NotNull
        Date checkIn,
        @NotNull
        Date checkOut
) {

}