package org.ukma.spring.crooodle.dto;

import lombok.Data;

@Data
public class HotelCriteriaDto {
    private String query;
    private Integer countryId;
    private Integer worldRegionId;
}
