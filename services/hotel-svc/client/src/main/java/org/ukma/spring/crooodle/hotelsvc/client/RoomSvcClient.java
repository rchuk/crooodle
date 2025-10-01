package org.ukma.spring.crooodle.hotelsvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomUpsertDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "hotel-svc")
public interface RoomSvcClient {
	@PostMapping("/hotel/{hotelId}/room")
	UUID create(@PathVariable("hotelId") UUID hotelId,
							@RequestBody RoomUpsertDto requestDto);

	@GetMapping("/room/{id}")
	RoomResponseDto read(@PathVariable("id") UUID id);

	@GetMapping("/hotel/{hotelId}/room")
	List<RoomResponseDto> readAllByHotel(@PathVariable("hotelId") UUID hotelId);

	@GetMapping("/room-type/{roomTypeId}/room")
	List<RoomResponseDto> readAllByType(@PathVariable("roomTypeId") UUID roomTypeId);

	@PutMapping("/room/{id}")
	void update(@PathVariable("id") UUID id, @RequestBody RoomResponseDto roomDtoToUpdate);

	@DeleteMapping("/room/{id}")
	void delete(@PathVariable("id") UUID id);
}
