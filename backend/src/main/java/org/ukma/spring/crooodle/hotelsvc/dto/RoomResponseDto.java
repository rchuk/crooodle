package org.ukma.spring.crooodle.hotelsvc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RoomResponseDto(
    @NotNull
    UUID id,
    @NotNull
    RoomTypeResponseDto type,
    @NotNull
    String name
) {

}