package org.ukma.spring.crooodle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
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
