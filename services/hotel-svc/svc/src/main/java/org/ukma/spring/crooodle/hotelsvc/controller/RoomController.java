package org.ukma.spring.crooodle.hotelsvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dtos.PageDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.service.RoomService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class RoomController {
	private final RoomService roomService;

	@PostMapping("/hotel/{hotelId}/room")
	public UUID create(@PathVariable UUID hotelId, @Valid @RequestBody RoomUpsertDto requestDto) {
		return roomService.create(hotelId, requestDto);
	}

	@GetMapping("/room/{id}")
	public RoomDto read(@PathVariable UUID id) {
		return roomService.get(id);
	}

	@PutMapping("/room/{id}")
	public void update(@PathVariable UUID id, @Valid @RequestBody RoomUpsertDto requestDto) {
		roomService.update(id, requestDto);
	}

	@DeleteMapping("/room/{id}")
	public void delete(@PathVariable UUID id) {
		roomService.delete(id);
	}

	@GetMapping("/hotel/{hotelId}/room")
	public PageDto<RoomDto> listHotelRooms(@PathVariable UUID hotelId, @PageableDefault(size = 10, sort = "name") Pageable pageable) {
		return PageDto.of(roomService.listHotelRooms(hotelId, pageable));
	}
}
