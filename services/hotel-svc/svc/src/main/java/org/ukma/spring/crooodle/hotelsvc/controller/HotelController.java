package org.ukma.spring.crooodle.hotelsvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.dtos.PageDto;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelDto;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.service.HotelService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class HotelController {
	private final HotelService hotelService;

	//@PreAuthorize("isAuthenticated()")
	@PostMapping("/hotel")
	public UUID create(@RequestBody HotelUpsertDto requestDto) {
		return hotelService.create(requestDto);
	}

	@GetMapping("/hotel/{id}")
	public HotelDto get(@PathVariable UUID id) {
		return hotelService.get(id);
	}

	@PutMapping("/hotel/{id}")
	public void update(@PathVariable UUID id, @RequestBody HotelUpsertDto requestDto) {
		hotelService.update(id, requestDto);
	}

	@DeleteMapping("/hotel/{id}")
	public void delete(@PathVariable UUID id) {
		hotelService.delete(id);
	}

	@GetMapping("/hotel")
	public PageDto<HotelDto> list(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
		return PageDto.of(hotelService.list(pageable));
	}
}
