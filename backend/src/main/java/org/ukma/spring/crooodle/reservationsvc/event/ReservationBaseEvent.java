package org.ukma.spring.crooodle.reservationsvc.event;

import java.util.UUID;

public interface ReservationBaseEvent {
    UUID getUserId();
    UUID getRoomId();
}
