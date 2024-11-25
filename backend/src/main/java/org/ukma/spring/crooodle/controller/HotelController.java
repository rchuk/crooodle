package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.HotelService;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService service;

    @GetMapping("/{id}")
    public HotelResponseDto get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @Valid
    @GetMapping
    public PageResponseDto<HotelResponseDto> list(HotelCriteriaDto criteriaDto) {
        return service.list(criteriaDto);
    }
}
