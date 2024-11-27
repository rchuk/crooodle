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
import org.ukma.spring.crooodle.service.RoomGroupService;

@RestController
@RequestMapping("/admin/room-groups")
@RequiredArgsConstructor
public class RoomGroupAdminController {

    private final RoomGroupService service;

    @Operation(operationId = "createRoomGroup")
    @PreAuthorize("hasAuthority('ROOM_GROUP_CREATE')")
    @PostMapping
    public long create(@RequestBody @Valid RoomGroupCreateRequestDto requestDto) {
        return service.create(requestDto);
    }

    @Operation(operationId = "getRoomGroupAdmin")
    @PreAuthorize("hasAuthority('ROOM_GROUP_VIEW')")
    @GetMapping("/{id}")
    public RoomGroupAdminResponseDto get(@PathVariable("id") long id) {
        return service.getAdmin(id);
    }

    @Operation(operationId = "editRoomGroup")
    @PreAuthorize("hasAuthority('ROOM_GROUP_EDIT')")
    @PutMapping("/{id}")
    public void edit(@PathVariable("id") long id,
                     @RequestBody @Valid RoomGroupAdminEditRequestDto requestDto) {
        service.edit(id, requestDto);
    }

    @Operation(operationId = "deleteRoomGroup")
    @PreAuthorize("hasAuthority('ROOM_GROUP_DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        service.delete(id);
    }

    @Operation(operationId = "listRoomGroupsAdmin")
    @PreAuthorize("hasAuthority('ROOM_GROUP_VIEW')")
    @GetMapping
    public PageResponseDto<RoomGroupAdminResponseDto> list(
        @ParameterObject @Valid RoomGroupCriteriaDto criteriaDto,
        @ParameterObject @Valid PaginationDto paginationDto
    ) {
        return service.listAdmin(criteriaDto, paginationDto);
    }
}
