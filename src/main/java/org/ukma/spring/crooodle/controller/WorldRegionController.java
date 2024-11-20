package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.WorldRegionCriteriaDto;
import org.ukma.spring.crooodle.dto.WorldRegionResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.WorldRegionService;

@RestController
@RequestMapping("/world-regions")
@RequiredArgsConstructor
public class WorldRegionController {
    private final WorldRegionService service;

    @Operation(operationId = "getWorldRegion")
    @GetMapping("/{id}")
    public WorldRegionResponseDto get(@PathVariable int id) {
        return service.get(id);
    }

    @Operation(operationId = "listWorldRegions")
    @GetMapping
    public PageResponseDto<WorldRegionResponseDto> list(@RequestParam @Valid WorldRegionCriteriaDto criteriaDto) {
        return service.list(criteriaDto);
    }
}
