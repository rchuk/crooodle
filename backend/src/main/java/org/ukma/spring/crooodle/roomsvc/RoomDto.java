package org.ukma.spring.crooodle.roomsvc;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RoomDto(
        @NotNull
        UUID id,

        @NotNull
        UUID typeId,

        @NotNull
        UUID hotelId,

        @NotNull
        String number,

        @NotNull
        boolean isOccupied
) {

}