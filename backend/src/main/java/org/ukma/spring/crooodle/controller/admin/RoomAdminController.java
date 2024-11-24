package org.ukma.spring.crooodle.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.*;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.RoomService;

@RestController
@RequestMapping("/hotels/{hotel_id}/rooms")
@RequiredArgsConstructor
public class RoomAdminController {

    private final RoomService service;

    @Operation(operationId = "createRoom")
    @PreAuthorize("hasAuthority('ROOM_CREATE')")
    @PostMapping
    public int create(@PathVariable("hotel_id") Long hotelId, @RequestBody @Valid RoomCreateRequestDto requestDto) {
        return service.create(hotelId, requestDto);
    }

    @Operation(operationId = "getRoomAdmin")
    @PreAuthorize("hasAuthority('ROOM_VIEW')")
    @GetMapping("/{room_id}")
    public RoomAdminResponseDto get(@PathVariable("hotel_id") Long hotelId, @PathVariable("room_id") Long roomId) {
        return service.getAdmin(hotelId, roomId);
    }

    @Operation(operationId = "editRoom")
    @PreAuthorize("hasAuthority('ROOM_EDIT')")
    @PutMapping("/{room_id}")
    public void edit(@PathVariable("hotel_id") Long hotelId, @PathVariable("room_id") Long roomId,
                     @RequestBody @Valid RoomEditRequestDto requestDto) {
        service.edit(hotelId, roomId, requestDto);
    }

    @Operation(operationId = "deleteRoom")
    @PreAuthorize("hasAuthority('ROOM_DELETE')")
    @DeleteMapping("/{room_id}")
    public void delete(@PathVariable("hotel_id") Long hotelId, @PathVariable("room_id") Long roomId) {
        service.delete(hotelId, roomId);
    }

    @Operation(operationId = "listRoomsAdmin")
    @PreAuthorize("hasAuthority('ROOM_VIEW')")
    @GetMapping
    public PageResponseDto<RoomAdminResponseDto> list(@PathVariable("hotel_id") Long hotelId,
                                                      @RequestParam(required = false) @Valid RoomCriteriaDto criteriaDto) {
        return service.listAdmin(hotelId, criteriaDto);
    }
}
