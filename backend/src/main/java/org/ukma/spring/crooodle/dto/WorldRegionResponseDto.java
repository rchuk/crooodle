package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class WorldRegionResponseDto {
    int id;
    String name;
    int countryCount;
}
