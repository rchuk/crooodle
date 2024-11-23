package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelAdminResponseDto {
    private Long id;
    private String name;
    private String address;
    private long rankSum;
    private int rankCount;
    private String countryName;
    private String regionName;
}
