package org.ukma.spring.crooodle.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.WorldRegionService;

@RestController
@RequestMapping("/admin/world-regions")
@RequiredArgsConstructor
public class WorldRegionAdminController {
    private final WorldRegionService service;

    @PostMapping
    public int create(@RequestBody @Valid WorldRegionCreateRequestDto requestDto) {
        return service.create(requestDto);
    }

    @GetMapping("/{id}")
    public WorldRegionAdminResponseDto get(@PathVariable int id) {
        return service.getAdmin(id);
    }

    @PutMapping("/{id}")
    public void edit(@PathVariable int id, @RequestBody @Valid WorldRegionEditRequestDto requestDto) {
        service.edit(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @GetMapping
    public PageResponseDto<WorldRegionAdminResponseDto> list(@RequestParam @Valid WorldRegionCriteriaDto criteriaDto) {
        return service.listAdmin(criteriaDto);
    }
}
