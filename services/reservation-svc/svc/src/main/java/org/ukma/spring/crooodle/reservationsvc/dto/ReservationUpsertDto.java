package org.ukma.spring.crooodle.reservationsvc.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.UUID;

public record ReservationUpsertDto (
        @NotNull
        UUID roomId,
        @NotNull
        Date checkInDate,
        @NotNull
        Date checkOutDate
) {}
