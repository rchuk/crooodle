package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.RoomCriteriaDto;
import org.ukma.spring.crooodle.dto.RoomResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.dto.common.PaginationDto;
import org.ukma.spring.crooodle.service.RoomService;

@RestController
@RequestMapping("/hotels/rooms")
@SecurityRequirements
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @Operation(operationId = "getRoom")
    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public RoomResponseDto get(@PathVariable("id") long id) {
        return service.get(id);
    }

    @Operation(operationId = "listRooms")
    @PreAuthorize("permitAll()")
    @GetMapping
    public PageResponseDto<RoomResponseDto> list(
        @ParameterObject @Valid RoomCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
    ) {
        return service.list(criteriaDto, paginationDto);
    }
}
