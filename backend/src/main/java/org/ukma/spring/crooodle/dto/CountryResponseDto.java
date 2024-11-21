package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CountryResponseDto {
    int id;
    String name;
    WorldRegionResponseDto worldRegion;
}
