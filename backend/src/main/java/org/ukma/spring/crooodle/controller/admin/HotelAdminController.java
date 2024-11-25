package org.ukma.spring.crooodle.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.HotelService;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
public class HotelAdminController {

    private final HotelService service;

    @Valid
    @PostMapping
    public Long create(@RequestBody HotelCreateRequestDto requestDto) {
        return service.create(requestDto);
    }

    @GetMapping("/{id}")
    public HotelAdminResponseDto get(@PathVariable Long id) {
        return service.getAdmin(id);
    }

    @PutMapping("/{id}")
    public void edit(@PathVariable Long id, @RequestBody HotelCreateRequestDto requestDto) {
        service.edit(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public PageResponseDto<HotelAdminResponseDto> list(HotelCriteriaDto criteriaDto) {
        return service.listAdmin(criteriaDto);
    }
}
