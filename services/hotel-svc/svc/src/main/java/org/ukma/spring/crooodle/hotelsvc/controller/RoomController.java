package org.ukma.spring.crooodle.hotelsvc.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

record AvailabilityDto(long roomId, boolean available) {}

@RestController
public class RoomController {

	@GetMapping(value="/api/rooms/{id}/availability", produces=MediaType.APPLICATION_JSON_VALUE)
	public AvailabilityDto availability(@PathVariable long id) {
		return new AvailabilityDto(id, id % 2 == 0);
	}

	@GetMapping(value="/", produces=MediaType.TEXT_HTML_VALUE)
	public String index() {
		return """
      <!doctype html><html><head><meta charset="utf-8"><title>hotel-svc</title></head>
      <body><h1>hotel-svc</h1><p>Status: OK</p></body></html>
      """;
	}

	@GetMapping(value="/api/rooms/export", produces="text/csv")
	public ResponseEntity<byte[]> exportCsv() {
		List<String> rows = List.of("id,name,type,price",
			"1,Deluxe,DOUBLE,120.0",
			"2,Economy,SINGLE,60.0");
		byte[] body = String.join("\n", rows).getBytes(StandardCharsets.UTF_8);
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rooms.csv\"")
			.body(body);
	}

	@GetMapping(value="/internal/api/ping", produces=MediaType.APPLICATION_JSON_VALUE)
	public String ping() { return "{\"ok\":true}"; }
}
