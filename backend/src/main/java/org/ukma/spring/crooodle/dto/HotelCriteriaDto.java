package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HotelCriteriaDto {
    private String name;
    private Integer countryId;
    private Integer regionId;
}
