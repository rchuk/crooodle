package org.ukma.spring.crooodle.hotelsvc.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record HotelUpsertDto(
    @NotNull
    @Size(min = 1, max = 200)
    String name,
    @NotNull
    @Size(min = 1, max = 200)
    String address
) {

}
