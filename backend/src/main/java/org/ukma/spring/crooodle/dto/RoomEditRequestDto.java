package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

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
