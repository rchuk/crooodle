package org.ukma.spring.crooodle.hotelsvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.service.RoomSvc;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class RoomController {
    private final RoomSvc roomSvc;

    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PostMapping("/hotel/{hotelId}/room")
    public UUID create(@PathVariable UUID hotelId, @Valid @RequestBody RoomUpsertDto requestDto) {
        return roomSvc.create(hotelId, requestDto);
    }

		// ----- READ -----
    @GetMapping("/room/{id}")
    public RoomResponseDto read(@PathVariable UUID id) {
        return roomSvc.read(id);
    }

		@GetMapping("/room/{id}/html")
		public String readRoomHtml(@PathVariable UUID id) {
			return roomSvc.readToHTML(id);
		}

		@GetMapping("/room/{id}/csv")
		public ResponseEntity<byte[]> readRoomCsv(@PathVariable UUID id) {
			byte[] csvData = roomSvc.readToCSV(id);
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"room-" + id + ".csv\"")
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(csvData);
		}

		// ----- READ ALL BY HOTEL -----
    @GetMapping("/hotel/{hotelId}/room")
    public List<RoomResponseDto> readAllByHotel(@PathVariable UUID hotelId) {
        return roomSvc.readAllByHotel(hotelId);
    }

		@GetMapping("/hotel/{hotelId}/room/html")
		public String readAllByHotelHtml(@PathVariable UUID hotelId) {
			return roomSvc.readAllByHotelToHTML(hotelId);
		}

		@GetMapping("/hotel/{hotelId}/room/csv")
		public ResponseEntity<byte[]> readAllByHotelCsv(@PathVariable UUID hotelId) {
			byte[] csvData = roomSvc.readAllByHotelToCSV(hotelId);
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rooms-" + hotelId + ".csv\"")
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(csvData);
		}


		// ----- READ ALL BY TYPE -----
    @GetMapping("/room-type/{roomTypeId}/room")
    public List<RoomResponseDto> readAllByType(@PathVariable UUID roomTypeId) {
        return roomSvc.readAllByType(roomTypeId);
    }

		@GetMapping("/room-type/{roomTypeId}/room/html")
		public String readAllByTypeHtml(@PathVariable UUID roomTypeId) {
			return roomSvc.readAllByTypeToHTML(roomTypeId);
		}

		@GetMapping("/room-type/{roomTypeId}/room/csv")
		public ResponseEntity<byte[]> readAllByTypeCsv(@PathVariable UUID roomTypeId) {
			byte[] csvData = roomSvc.readAllByTypeToCSV(roomTypeId);
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rooms-" + roomTypeId + ".csv\"")
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(csvData);
		}


		// ----- READ ALL BY TYPE AND HOTEL -----
		@GetMapping("hotel/{hotelId}/room/{roomTypeId}/room")
		public List<RoomResponseDto> readAllByTypeAndHotel(@PathVariable UUID hotelId, @PathVariable UUID roomTypeId) {
			return roomSvc.readAllByHotelAndType(hotelId, roomTypeId);
		}

		@GetMapping(value = "/hotel/{hotelId}/type/{typeId}/html", produces = MediaType.TEXT_HTML_VALUE)
		public ResponseEntity<String> readToHTML(@PathVariable UUID hotelId, @PathVariable UUID typeId) {
			String html = roomSvc.readAllByHotelAndTypeAsHtml(hotelId, typeId);
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
				.body(html);
		}

		@GetMapping(value = "/hotel/{hotelId}/type/{typeId}/csv", produces = "text/csv")
		public ResponseEntity<byte[]> getRoomsByHotelAndTypeCsv(@PathVariable UUID hotelId, @PathVariable UUID typeId) {
			byte[] csvData = roomSvc.readAllByHotelAndTypeAsCsv(hotelId, typeId);
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"rooms-" + hotelId + "-" + typeId + ".csv\"")
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(csvData);
		}

		// ----- UPDATE -----
    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @PutMapping("/room/{id}")
    public void update(@PathVariable UUID id, @RequestBody RoomResponseDto roomDtoToUpdate) {
        roomSvc.update(id, roomDtoToUpdate);
    }

	// ----- DELETE -----
    @PreAuthorize("hasRole('HOTEL_OWNER')")
    @DeleteMapping("/room/{id}")
    public void delete(@PathVariable UUID id) {
        roomSvc.delete(id);
    }
}

