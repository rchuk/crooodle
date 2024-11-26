package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HotelAdminResponseDto {
    long id;
    String name;
    String address;
    long rankSum;
    int rankCount;
    CountryResponseDto country;
}
