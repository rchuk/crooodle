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
import org.ukma.spring.crooodle.service.RoomService;

@RestController
@RequestMapping("/admin/rooms")
@RequiredArgsConstructor
public class RoomAdminController {

    private final RoomService service;

    @Operation(operationId = "createRoom")
    @PreAuthorize("hasAuthority('ROOM_CREATE')")
    @PostMapping
    public long create(@RequestBody @Valid RoomCreateRequestDto requestDto) {
        return service.create(requestDto);
    }

    @Operation(operationId = "getRoomAdmin")
    @PreAuthorize("hasAuthority('ROOM_VIEW')")
    @GetMapping("/{id}")
    public RoomAdminResponseDto get(@PathVariable("id") long id) {
        return service.getAdmin(id);
    }

    @Operation(operationId = "editRoom")
    @PreAuthorize("hasAuthority('ROOM_EDIT')")
    @PutMapping("/{id}")
    public void edit(@PathVariable("id") long id,
                     @RequestBody @Valid RoomEditRequestDto requestDto) {
        service.edit(id, requestDto);
    }

    @Operation(operationId = "deleteRoom")
    @PreAuthorize("hasAuthority('ROOM_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @Operation(operationId = "listRoomsAdmin")
    @PreAuthorize("hasAuthority('ROOM_VIEW')")
    @GetMapping
    public PageResponseDto<RoomAdminResponseDto> list(
        @ParameterObject @Valid RoomCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
        ) {
        return service.listAdmin(criteriaDto, paginationDto);
    }
}
