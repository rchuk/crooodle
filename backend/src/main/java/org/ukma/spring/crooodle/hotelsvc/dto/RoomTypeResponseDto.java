package org.ukma.spring.crooodle.hotelsvc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RoomTypeResponseDto(
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
