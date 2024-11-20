package org.ukma.spring.crooodle.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.CountryService;

@RestController
@RequestMapping("/admin/countries")
@RequiredArgsConstructor
public class CountryAdminController {
    private final CountryService service;

    @PostMapping
    public int create(@RequestBody @Valid CountryCreateRequestDto requestDto) {
        return service.create(requestDto);
    }

    @GetMapping("/{id}")
    public CountryAdminResponseDto get(@PathVariable int id) {
        return service.getAdmin(id);
    }

    @PutMapping("/{id}")
    public void edit(@PathVariable int id, @RequestBody @Valid CountryEditRequestDto requestDto) {
        service.edit(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping
    public PageResponseDto<CountryAdminResponseDto> list(@RequestParam @Valid CountryCriteriaDto criteriaDto) {
        return service.listAdmin(criteriaDto);
    }
}
