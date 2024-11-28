package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.HotelService;

@RestController
@RequestMapping("/hotels")
@SecurityRequirements
@RequiredArgsConstructor
public class HotelController {
    private final HotelService service;

    @Operation(operationId = "getHotel")
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public HotelResponseDto get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @Operation(operationId = "listHotels")
    @PreAuthorize("permitAll()")
    @GetMapping
    public PageResponseDto<HotelResponseDto> list(
        @ParameterObject @Valid HotelCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
    ) {
        return service.list(criteriaDto, paginationDto);
    }
}
