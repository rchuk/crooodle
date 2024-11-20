package org.ukma.spring.crooodle.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/{id}")
    public CountryResponseDto get(@PathVariable int id) {
        return service.get(id);
    }

    @GetMapping
    public PageResponseDto<CountryResponseDto> list(@RequestParam @Valid CountryCriteriaDto criteriaDto) {
        return service.list(criteriaDto);
    }
}
