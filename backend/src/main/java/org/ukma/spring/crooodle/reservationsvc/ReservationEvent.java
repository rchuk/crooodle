package org.ukma.spring.crooodle.reservationsvc;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReservationEvent(
   @NotNull
   ReservationEventType type,
   @NotNull
   UUID userId,
   @NotNull
   UUID hotelId,
   @NotNull
   UUID roomId

   // TODO: Add hotel or room id
) {}
