package org.ukma.spring.crooodle.hotelsvc;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record HotelDto(
    @NotNull
    UUID id,
    @NotNull
    String name,
    @NotNull
    String address
) {

}
