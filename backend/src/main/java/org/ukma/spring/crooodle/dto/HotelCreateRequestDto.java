package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class HotelCreateRequestDto {

    @NotBlank
    String name;

    @NotBlank
    String address;

    int countryId;

    Integer regionId;

    AmenitiesDto amenities;
}
