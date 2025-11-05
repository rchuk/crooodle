package org.ukma.spring.crooodle.reservationsvc.dto;

import java.util.UUID;

public interface ReservationBaseEvent {
    UUID getUserId();
    UUID getRoomId();
}
