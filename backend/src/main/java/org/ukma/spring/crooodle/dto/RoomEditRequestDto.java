package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
public class RoomEditRequestDto {
    @NotBlank
    String name;

    @PositiveOrZero
    int capacity;

    @PositiveOrZero
    double pricePerNight;

    String description;
}
