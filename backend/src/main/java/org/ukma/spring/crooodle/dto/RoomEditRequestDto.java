package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RoomEditRequestDto {
    @NotBlank
    private String name;

    @PositiveOrZero
    private int capacity;

    @PositiveOrZero
    private double pricePerNight;

    private String description;

    private boolean available;
}
