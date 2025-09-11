package org.ukma.spring.crooodle.hotelsvc.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record HotelResponseDto(
    @NotNull
    UUID id,
    @NotNull
    String name,
    @NotNull
    String address
) {

}
