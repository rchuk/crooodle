package org.ukma.spring.crooodle.hotelsvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelUpsertDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "hotel-svc")
@RequestMapping("/hotel")
public interface HotelSvcClient {
	@PostMapping
	UUID create(@RequestBody HotelUpsertDto hotelUpsertDto);

	@GetMapping("/{id}")
	HotelResponseDto read(@PathVariable("id") UUID id);

	@GetMapping
	List<HotelResponseDto> readAll();

	@PutMapping("/{id}")
	void update(@PathVariable("id") UUID id, @RequestBody HotelUpsertDto hotelUpsertDto);

	@DeleteMapping("/{id}")
	void delete(@PathVariable("id") UUID id);
}
