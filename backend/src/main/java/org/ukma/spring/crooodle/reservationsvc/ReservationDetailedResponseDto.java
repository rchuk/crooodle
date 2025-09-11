package org.ukma.spring.crooodle.reservationsvc;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomResponseDto;
import org.ukma.spring.crooodle.usersvc.UserResponseDto;

import java.util.Date;
import java.util.UUID;

@Builder
public record ReservationDetailedResponseDto(
    @NotNull
    UUID id,
    @NotNull
    RoomResponseDto room,
    @NotNull
    UserResponseDto user,
    @NotNull
    int price,
    @NotNull
    Date checkInDate,
    @NotNull
    Date checkOutDate,
    @NotNull
    ReservationState state
) {

}