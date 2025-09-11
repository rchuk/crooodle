package org.ukma.spring.crooodle.reservationsvc;

import java.util.Date;
import java.util.Set;

public record ReservationCriteriaDto(
    Set<ReservationState> states,
    Date checkInDate,
    Date checkOutDate
) {

}
