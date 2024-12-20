package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.WorldRegionCriteriaDto;
import org.ukma.spring.crooodle.dto.WorldRegionResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.WorldRegionService;

@RestController
@RequestMapping("/world-regions")
@SecurityRequirements
@RequiredArgsConstructor
public class WorldRegionController {
    private final WorldRegionService service;

    @Operation(operationId = "getWorldRegion")
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public WorldRegionResponseDto get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @Operation(operationId = "listWorldRegions")
    @PreAuthorize("permitAll()")
    @GetMapping
    public PageResponseDto<WorldRegionResponseDto> list(
        @ParameterObject @Valid WorldRegionCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
    ) {
        return service.list(criteriaDto, paginationDto);
    }
}
