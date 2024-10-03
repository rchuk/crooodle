package org.ukma.spring.crooodle.dto;

import lombok.Value;

@Value
public class HotelUpdateRequestDto {
    String name;
    String address;
    Double latitude;
    Double longitude;
}
