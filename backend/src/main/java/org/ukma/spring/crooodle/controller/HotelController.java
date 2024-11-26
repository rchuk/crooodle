package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.HotelService;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService service;

    @Operation(operationId = "getHotel")
    @GetMapping("/{id}")
    public HotelResponseDto get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @Operation(operationId = "listHotels")
    @Valid
    @GetMapping
    public PageResponseDto<HotelResponseDto> list(
        @ParameterObject @Valid HotelCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
    ) {
        return service.list(criteriaDto, paginationDto);
    }
}
