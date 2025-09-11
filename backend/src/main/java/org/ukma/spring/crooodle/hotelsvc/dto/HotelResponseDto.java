package org.ukma.spring.crooodle.hotelsvc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record HotelResponseDto(
    @NotNull
    UUID id,
    @NotNull
    UUID ownerId,
    @NotNull
    String name,
    @NotNull
    String address,
    @NotNull
    long roomCount
) {

}
