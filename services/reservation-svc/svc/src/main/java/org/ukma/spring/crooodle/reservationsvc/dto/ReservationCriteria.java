package org.ukma.spring.crooodle.reservationsvc.dto;

import java.time.LocalDate;

public record ReservationCriteria (
	LocalDate checkInDate,
	LocalDate checkOutDate
) {}
