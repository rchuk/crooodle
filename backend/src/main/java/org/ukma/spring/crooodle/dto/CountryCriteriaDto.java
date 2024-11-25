package org.ukma.spring.crooodle.dto;

import lombok.Builder;
import lombok.Value;
import org.springdoc.api.annotations.ParameterObject;
import org.ukma.spring.crooodle.dto.common.PaginationDto;

@Builder
@ParameterObject
@Value
public class CountryCriteriaDto {
    String query;
    PaginationDto pagination;
}
