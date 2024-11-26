package org.ukma.spring.crooodle.controller.admin;

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
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
public class HotelAdminController {

    private final HotelService service;

    @Operation(operationId = "createHotel")
    @PostMapping
    public long create(@RequestBody @Valid HotelCreateRequestDto requestDto) {
        return service.create(requestDto);
    }

    @Operation(operationId = "getHotelAdmin")
    @GetMapping("/{id}")
    public HotelAdminResponseDto get(@PathVariable("id") long id) {
        return service.getAdmin(id);
    }

    @Operation(operationId = "editHotel")
    @PutMapping("/{id}")
    public void edit(@PathVariable("id") long id, @RequestBody @Valid HotelEditRequestDto requestDto) {
        service.edit(id, requestDto);
    }

    @Operation(operationId = "deleteHotel")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @Operation(operationId = "listHotelsAdmin")
    @GetMapping
    public PageResponseDto<HotelAdminResponseDto> list(
        @ParameterObject @Valid HotelCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
    ) {
        return service.listAdmin(criteriaDto, paginationDto);
    }
}
