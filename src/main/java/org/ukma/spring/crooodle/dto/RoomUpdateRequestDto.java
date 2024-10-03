package org.ukma.spring.crooodle.dto;

import lombok.Value;

@Value
public class RoomUpdateRequestDto {
    String number;
    Integer pricePerNight;
}
