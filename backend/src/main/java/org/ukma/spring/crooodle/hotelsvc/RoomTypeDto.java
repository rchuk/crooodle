package org.ukma.spring.crooodle.hotelsvc;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

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
