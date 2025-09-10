package org.ukma.spring.crooodle.reservationsvc;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReservationEvent(
   ReservationEventType type,
   UUID userId
   // TODO: Add hotel or room id
) {}
