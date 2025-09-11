package org.ukma.spring.crooodle.reservationsvc;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomResponseDto;

import java.util.Date;
import java.util.UUID;

@Builder
public record ReservationResponseDto(
    @NotNull
    UUID id,
    @NotNull
    RoomResponseDto room,
    @NotNull
    Date checkInDate,
    @NotNull
    Date checkOutDate
) {

}