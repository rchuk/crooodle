package org.ukma.spring.crooodle.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.CountryService;

@RestController
@RequestMapping("/admin/countries")
@RequiredArgsConstructor
public class CountryAdminController {
    private final CountryService service;

    @Operation(operationId = "createCountry")
    @PreAuthorize("hasAuthority('COUNTRY_CREATE')")
    @PostMapping
    public int create(@RequestBody @Valid CountryCreateRequestDto requestDto) {
        return service.create(requestDto);
    }

    @Operation(operationId = "getCountryAdmin")
    @PreAuthorize("hasAuthority('COUNTRY_VIEW')")
    @GetMapping("/{id}")
    public CountryAdminResponseDto get(@PathVariable("id") int id) {
        return service.getAdmin(id);
    }

    @Operation(operationId = "editCountry")
    @PreAuthorize("hasAuthority('COUNTRY_EDIT')")
    @PutMapping("/{id}")
    public void edit(@PathVariable("id") int id, @RequestBody @Valid CountryEditRequestDto requestDto) {
        service.edit(id, requestDto);
    }

    @Operation(operationId = "deleteCountry")
    @PreAuthorize("hasAuthority('COUNTRY_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    @Operation(operationId = "listCountriesAdmin")
    @PreAuthorize("hasAuthority('COUNTRY_VIEW')")
    @GetMapping
    public PageResponseDto<CountryAdminResponseDto> list(
        @ParameterObject @Valid CountryCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
    ) {
        return service.listAdmin(criteriaDto, paginationDto);
    }
}
