package org.ukma.spring.crooodle.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.WorldRegionService;

@RestController
@RequestMapping("/admin/world-regions")
@RequiredArgsConstructor
public class WorldRegionAdminController {
    private final WorldRegionService service;

    @Operation(operationId = "createWorldRegion")
    @PreAuthorize("hasAuthority('WORLD_REGION_CREATE')")
    @PostMapping
    public int create(@RequestBody @Valid WorldRegionCreateRequestDto requestDto) {
        return service.create(requestDto);
    }

    @Operation(operationId = "getWorldRegionAdmin")
    @PreAuthorize("hasAuthority('WORLD_REGION_VIEW')")
    @GetMapping("/{id}")
    public WorldRegionAdminResponseDto get(@PathVariable("id") int id) {
        return service.getAdmin(id);
    }

    @Operation(operationId = "editWorldRegion")
    @PreAuthorize("hasAuthority('WORLD_REGION_EDIT')")
    @PutMapping("/{id}")
    public void edit(@PathVariable("id") int id, @RequestBody @Valid WorldRegionEditRequestDto requestDto) {
        service.edit(id, requestDto);
    }

    @Operation(operationId = "deleteWorldRegion")
    @PreAuthorize("hasAuthority('WORLD_REGION_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    @Operation(operationId = "listWorldRegionsAdmin")
    @PreAuthorize("hasAuthority('WORLD_REGION_VIEW')")
    @GetMapping
    public PageResponseDto<WorldRegionAdminResponseDto> list(@RequestParam(name = "criteria", required = false) @Valid WorldRegionCriteriaDto criteriaDto) {
        return service.listAdmin(criteriaDto);
    }
}
