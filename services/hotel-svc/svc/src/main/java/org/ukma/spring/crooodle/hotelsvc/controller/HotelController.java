package org.ukma.spring.crooodle.hotelsvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.hotelsvc.service.HotelSvc;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelUpsertDto;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("hotels")
public class HotelController {
	private final HotelSvc svc;

	@PreAuthorize("hasRole('HOTEL_OWNER')")
	@PostMapping
	public UUID create(@Valid @RequestBody HotelUpsertDto hotelUpsertDto) {
		return svc.create(hotelUpsertDto);
	}

	// ----- READ -----
	@GetMapping("/{id}")
	public HotelResponseDto read(@PathVariable UUID id) {
		return svc.read(id);
	}

	@GetMapping("/{id}/html")
	public ResponseEntity<String> readToHTML(@PathVariable UUID id) {
		String html = svc.readToHTML(id);
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
			.body(html);
	}

	@GetMapping("/{id}/csv")
	public ResponseEntity<byte[]> readToCSV(@PathVariable UUID id) {
		byte[] csvBytes = svc.readToCSV(id);
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"hotel-" + id + ".csv\"")
			.contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
			.body(csvBytes);
	}

	// ----- READ ALL -----
	@GetMapping
	public List<HotelResponseDto> readAll() {
		return svc.readAll();
	}

	@GetMapping("/html")
	public ResponseEntity<String> readAllToHTML() {
		String html = svc.readAllToHTML();
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
			.body(html);
	}

	@GetMapping("/csv")
	public ResponseEntity<byte[]> readAllToCSV() {
		byte[] csvBytes = svc.readAllToCSV();
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"hotels.csv\"")
			.contentType(MediaType.parseMediaType("text/csv"))
			.body(csvBytes);
	}

	// ----- UPDATE -----
	@PreAuthorize("hasRole('HOTEL_OWNER')")
	@PutMapping("/{id}")
	public void update(@PathVariable UUID id, @Valid @RequestBody HotelUpsertDto hotelUpsertDto) {
		svc.update(id, hotelUpsertDto);
	}

	// ----- DELETE -----
	@PreAuthorize("hasRole('HOTEL_OWNER')")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		svc.delete(id);
	}
}
