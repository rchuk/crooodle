package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HotelResponseDto {
    long id;
    String name;
    String address;
    CountryResponseDto country;
    AmenitiesDto amenities;
}
