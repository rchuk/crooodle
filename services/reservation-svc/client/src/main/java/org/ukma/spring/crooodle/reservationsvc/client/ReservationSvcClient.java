package org.ukma.spring.crooodle.reservationsvc.client;


import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationCreateDto;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationCriteriaDto;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationDetailedResponseDto;
import org.ukma.spring.crooodle.reservationsvc.dto.ReservationResponseDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "reservation-svc")
public interface ReservationSvcClient {
	@PostMapping("/room/{roomId}/reservation")
	UUID create(@PathVariable UUID roomId, @Valid @RequestBody ReservationCreateDto requestDto);

	@GetMapping("/reservation/{id}")
	ReservationDetailedResponseDto read(@PathVariable UUID id);

	@GetMapping("/room/{roomId}/reservation")
	List<ReservationResponseDto> readAllByRoom(@PathVariable UUID roomId, @RequestBody(required = false) ReservationCriteriaDto requestDto);

	@PostMapping("/reservation/{id}/confirm")
	void confirm(@PathVariable UUID id);

	@PostMapping("/reservation/{id}/cancel")
	void cancel(@PathVariable UUID id);
}
