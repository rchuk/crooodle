package org.ukma.spring.crooodle.reservationsvc.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public record ReservationDto (
    @NotNull
    UUID id,
    @NotNull
    UUID roomId,
    @NotNull
    UUID profileId,
    @NotNull
    Date checkInDate,
    @NotNull
    Date checkOutDate
) {}
