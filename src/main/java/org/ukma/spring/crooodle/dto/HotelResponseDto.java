package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class HotelResponseDto {
    long id;
    String name;
    String address;
    double ranking;
    int totalRanks;
    double latitude;
    double longitude;
}
