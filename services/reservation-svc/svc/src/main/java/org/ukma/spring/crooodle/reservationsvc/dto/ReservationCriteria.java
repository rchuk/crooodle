package org.ukma.spring.crooodle.reservationsvc.dto;

import java.util.Date;

public record ReservationCriteria (
    Date checkInDate,
    Date checkOutDate
) {}
