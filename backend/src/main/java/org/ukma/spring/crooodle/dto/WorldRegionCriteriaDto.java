package org.ukma.spring.crooodle.dto;

import lombok.Value;
import org.springdoc.api.annotations.ParameterObject;
import org.ukma.spring.crooodle.dto.common.PaginationDto;

@ParameterObject
@Value
public class WorldRegionCriteriaDto {
    PaginationDto pagination;
}
