package org.ukma.spring.crooodle.roomsvc;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record RoomDto(
        @NotNull
        UUID id,

        @NotNull
        String number,

        @NotNull
        boolean isOccupied
) {

}