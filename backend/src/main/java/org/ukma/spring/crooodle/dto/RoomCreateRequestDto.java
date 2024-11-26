package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoomCreateRequestDto {

    @NotBlank
    private String name;

    @PositiveOrZero
    private int capacity;

    @PositiveOrZero
    private double pricePerNight;

    private String description;

    @NotNull
    private int roomTypeId;

    private boolean available;
}
