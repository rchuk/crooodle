package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.CountryCriteriaDto;
import org.ukma.spring.crooodle.dto.CountryResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.CountryService;

@RestController
@RequestMapping("/countries")
@SecurityRequirements
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;

    @Operation(operationId = "getCountry")
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public CountryResponseDto get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @Operation(operationId = "listCountries")
    @PreAuthorize("permitAll()")
    @GetMapping
    public PageResponseDto<CountryResponseDto> list(
        @ParameterObject @Valid CountryCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
    ) {
        return service.list(criteriaDto, paginationDto);
    }
}
