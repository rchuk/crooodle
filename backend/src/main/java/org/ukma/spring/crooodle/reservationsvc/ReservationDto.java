package org.ukma.spring.crooodle.reservationsvc;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ReservationDto(
    @NotNull
    UUID id,
    @NotNull
    UUID roomId,
    @NotNull
    UUID userId,
    @NotNull
    int price,
    @NotNull
    Date checkIn,
    @NotNull
    Date checkOut,

    @NotNull
    ReservationState state
) {

}