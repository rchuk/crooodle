package org.ukma.spring.crooodle.controller;

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

    @GetMapping("/{id}")
    public WorldRegionResponseDto get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping
    public PageResponseDto<WorldRegionResponseDto> list(@RequestParam @Valid WorldRegionCriteriaDto criteriaDto) {
        return service.list(criteriaDto);
    }
}
