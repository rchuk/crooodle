package org.ukma.spring.crooodle.dto;

import lombok.Value;
import org.ukma.spring.crooodle.dto.common.PaginationDto;

@Value
public class CountryCriteriaDto {
    String query;
    PaginationDto pagination;
}
