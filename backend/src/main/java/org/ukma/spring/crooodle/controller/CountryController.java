package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.CountryCriteriaDto;
import org.ukma.spring.crooodle.dto.CountryResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.CountryService;

@RestController
@RequestMapping("/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;

    @Operation(operationId = "getCountry")
    @PreAuthorize("permitAll()")
    @GetMapping
    public CountryResponseDto get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @Operation(operationId = "listCountries")
    @PreAuthorize("permitAll()")
    @GetMapping
    public PageResponseDto<CountryResponseDto> list(@RequestParam(name = "criteria", required = false) @Valid CountryCriteriaDto criteriaDto) {
        return service.list(criteriaDto);
    }
}
