package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelResponseDto {
    private Long id;
    private String name;
    private String address;
    private String countryName;
    private String regionName;
}
