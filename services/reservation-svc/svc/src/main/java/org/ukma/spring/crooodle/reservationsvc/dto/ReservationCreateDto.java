package org.ukma.spring.crooodle.reservationsvc.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationCreateDto (
    @NotNull
    UUID roomId,
    @NotNull
		LocalDate checkInDate,
    @NotNull
		LocalDate checkOutDate
) {}
