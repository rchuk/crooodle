package org.ukma.spring.crooodle.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dto.RoomCriteriaDto;
import org.ukma.spring.crooodle.dto.RoomResponseDto;
import org.ukma.spring.crooodle.dto.common.PageResponseDto;
import org.ukma.spring.crooodle.service.RoomService;

@RestController
@RequestMapping("/hotels/{hotel_id}/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @Operation(operationId = "getRoom")
    @PreAuthorize("permitAll()")
    @GetMapping("/{room_id}")
    public RoomResponseDto get(@PathVariable("hotel_id") Long hotelId, @PathVariable("room_id") Long roomId) {
        return service.get(hotelId, roomId);
    }

    @Operation(operationId = "listRooms")
    @PreAuthorize("permitAll()")
    @GetMapping
    public PageResponseDto<RoomResponseDto> list(@PathVariable("hotel_id") Long hotelId,
                                                 @RequestParam(required = false) @Valid RoomCriteriaDto criteriaDto) {
        return service.list(hotelId, criteriaDto);
    }
}
