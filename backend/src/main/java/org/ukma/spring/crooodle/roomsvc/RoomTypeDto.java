package org.ukma.spring.crooodle.roomsvc;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record RoomTypeDto(
        @NotNull
        UUID id,
        @NotNull
        UUID hotelId,

        @NotNull
        String name,

        @NotNull
        int price
) {

}
