package org.ukma.spring.crooodle.hotelsvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomTypeResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomTypeUpsertDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "hotel-svc")
public interface RoomTypeSvcClient {
	@PostMapping("/hotel/{hotelId}/room-type")
	UUID create(@PathVariable("hotelId") UUID hotelId,
							@RequestBody RoomTypeUpsertDto requestDto);

	@GetMapping("/room-type/{id}")
	RoomTypeResponseDto read(@PathVariable("id") UUID id);

	@GetMapping("/hotel/{hotelId}/room-type")
	List<RoomTypeResponseDto> readAllByHotel(@PathVariable("hotelId") UUID hotelId);

	@PutMapping("/room-type/{id}")
	void update(@PathVariable("id") UUID id,
							@RequestBody RoomTypeResponseDto requestDto);

	@DeleteMapping("/room-type/{id}")
	void delete(@PathVariable("id") UUID id);
}
