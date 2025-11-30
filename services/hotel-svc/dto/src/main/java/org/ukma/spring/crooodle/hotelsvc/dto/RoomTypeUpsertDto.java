package org.ukma.spring.crooodle.hotelsvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RoomTypeUpsertDto(
    @Size(min = 1, max = 100)
    @NotBlank
    String name,
    @Positive
    int price
) {

}
