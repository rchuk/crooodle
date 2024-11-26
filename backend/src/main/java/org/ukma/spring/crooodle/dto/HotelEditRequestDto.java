package org.ukma.spring.crooodle.dto;

import lombok.Value;

@Value
public class HotelEditRequestDto {
    String name;
    String address;
    String countryId;
    Integer regionId;
    AmenitiesDto amenities;
}
