package org.ukma.spring.crooodle.reservationsvc.event;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReservationCanceledEvent(
    @NotNull
    UUID userId,
    @NotNull
    UUID roomId
) implements ReservationBaseEvent {
    @Override
    public UUID getUserId() {
        return userId;
    }

    @Override
    public UUID getRoomId() {
        return roomId;
    }
}
