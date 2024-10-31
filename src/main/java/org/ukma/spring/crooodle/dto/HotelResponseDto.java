package org.ukma.spring.crooodle.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Builder
@Data
public class HotelResponseDto {
    long id;
    String name;
    String address;
    double ranking;
    int totalRanks;
    double latitude;
    double longitude;

    public HotelResponseDto(long id, String name, String address, double ranking, int totalRanks, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

}
