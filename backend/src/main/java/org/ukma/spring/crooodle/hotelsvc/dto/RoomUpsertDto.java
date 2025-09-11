package org.ukma.spring.crooodle.hotelsvc.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RoomUpsertDto(
    @NotNull
    UUID roomTypeId,
    @NotNull
    String name
) {
    
}
