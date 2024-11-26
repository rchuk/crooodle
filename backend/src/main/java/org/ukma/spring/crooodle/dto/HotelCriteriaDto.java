package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HotelCriteriaDto {
    private String query;
    private Integer countryId;
}
