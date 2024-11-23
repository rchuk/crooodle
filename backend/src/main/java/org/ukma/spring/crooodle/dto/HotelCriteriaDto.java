package org.ukma.spring.crooodle.dto;

import lombok.Data;

@Data
public class HotelCriteriaDto {
    private String name;
    private Integer countryId;
    private Integer regionId;
}
