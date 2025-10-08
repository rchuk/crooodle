package org.ukma.spring.crooodle.hotelsvc.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

record AvailabilityDto(long roomId, boolean available) {}

@RestController
public class RoomController {

	@RestController
	@RequestMapping("/api/rooms")
	static class PublicRoomsController {

		@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
		public List<Map<String, Object>> listRoomsJson() {
			return List.of(
				Map.of("id", 1, "name", "Deluxe", "price", 100),
				Map.of("id", 2, "name", "Suite", "price", 180)
			);
		}

		@GetMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE)
		public String listRoomsHtml() {
			return """
        <html><body>
        <h1>Rooms</h1>
        <ul>
          <li>Deluxe - $100</li>
          <li>Suite - $180</li>
        </ul>
        </body></html>
        """;
		}

		@GetMapping(value = "/export", produces = "text/csv")
		public ResponseEntity<byte[]> exportCsv() {
			List<String> rows = List.of(
				"id,name,type,price",
				"1,Deluxe,DOUBLE,120.0",
				"2,Economy,SINGLE,60.0"
			);
			byte[] body = String.join("\n", rows).getBytes(StandardCharsets.UTF_8);
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rooms.csv\"")
				.body(body);
		}
	}

	@GetMapping(value="/internal/api/ping", produces=MediaType.APPLICATION_JSON_VALUE)
	public String ping() {
		return "{\"ok\":true}";
	}

	@GetMapping(value="/internal/api/rooms/{id}/availability", produces=MediaType.APPLICATION_JSON_VALUE)
	public AvailabilityDto availabilityInternal(@PathVariable long id) {
		return new AvailabilityDto(id, id % 2 == 0);
	}

	@GetMapping(value="/", produces=MediaType.TEXT_HTML_VALUE)
	public String index() {
		return """
      <!doctype html><html><head><meta charset="utf-8"><title>hotel-svc</title></head>
      <body><h1>hotel-svc</h1><p>Status: OK</p></body></html>
      """;
	}
}
