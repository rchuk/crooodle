package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class WorldRegionAdminResponseDto {
    int id;
    String name;
    List<CountryResponseDto> countries;
}
