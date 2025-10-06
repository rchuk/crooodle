package org.ukma.spring.crooodle.usersvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.client.HotelClient;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelProxyController {

	private final HotelClient hotelClient;

	@GetMapping(value = "/rooms/{id}/availability", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HotelClient.AvailabilityDto> availability(@PathVariable long id) {
		return ResponseEntity.ok(hotelClient.getAvailabilityInternal(id));
	}

	@GetMapping(value = "/rooms", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> rooms() {
		return ResponseEntity.ok(hotelClient.getRooms());
	}

	@GetMapping(value = "/rooms/html", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> roomsHtml() {
		return ResponseEntity.ok(hotelClient.getRoomsHtml());
	}

	@GetMapping(value = "/rooms/export", produces = "text/csv")
	public ResponseEntity<byte[]> exportRoomsCsv() {
		return ResponseEntity.ok()
			.header("Content-Disposition", "attachment; filename=rooms.csv")
			.body(hotelClient.exportRoomsCsv());
	}
}
