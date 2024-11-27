package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.RoomGroupCriteriaDto;
import org.ukma.spring.crooodle.dto.RoomGroupResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.RoomGroupService;

@RestController
@RequestMapping("/room-groups")
@SecurityRequirements
@RequiredArgsConstructor
public class RoomGroupController {

    private final RoomGroupService service;

    @Operation(operationId = "getRoomGroup")
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public RoomGroupResponseDto get(@PathVariable("id") long id) {
        return service.get(id);
    }

    @Operation(operationId = "listRoomGroups")
    @PreAuthorize("permitAll()")
    @GetMapping
    public PageResponseDto<RoomGroupResponseDto> list(
        @ParameterObject @Valid RoomGroupCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
    ) {
        return service.list(criteriaDto, paginationDto);
    }
}
