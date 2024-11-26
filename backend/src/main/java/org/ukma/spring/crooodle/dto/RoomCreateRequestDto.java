package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class RoomCreateRequestDto {
    @NotBlank
    String name;

    @PositiveOrZero
    int capacity;

    @PositiveOrZero
    double pricePerNight;

    String description;

    @NotNull
    int roomTypeId;
}
