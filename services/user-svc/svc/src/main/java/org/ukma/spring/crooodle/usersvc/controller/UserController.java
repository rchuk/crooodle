package org.ukma.spring.crooodle.usersvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;
import org.ukma.spring.crooodle.usersvc.service.UserSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserSvc userSvc;

	// ----- READ ALL -----
	@GetMapping
	public List<UserResponseDto> getAll(){
		return userSvc.getAllUsers();
	}

	@GetMapping(value = "/html", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getAllUsersAsHtml() {
		String html = userSvc.getAllToHTML();
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
			.body(html);
	}

	@GetMapping(value = "/csv", produces = "text/csv")
	public ResponseEntity<byte[]> getAllUsersAsCsv() {
		byte[] csvData = userSvc.getAllToCSV();
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"users.csv\"")
			.contentType(MediaType.parseMediaType("text/csv"))
			.body(csvData);
	}

	// ----- READ -----
	@GetMapping("/{id}")
	public UserResponseDto getUser(@PathVariable UUID id){
		return userSvc.getUserById(id);
	}

	@GetMapping(value = "/{id}/html", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getByIdToHTML(@PathVariable UUID id){
		String html = userSvc.getUserByIdToHTML(id);
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
			.body(html);
	}

	@GetMapping(value = "/{id}/csv", produces = "text/csv")
	public ResponseEntity<byte[]> getByIdToCSV(@PathVariable UUID id) {
		byte[] csvBytes = userSvc.getUserByIdAsCSV(id);

		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"user-" + id + ".csv\"")
			.header(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8")
			.body(csvBytes);
	}

	@GetMapping("/{email}")
	public UserResponseDto getUserByEmail(@PathVariable String email){
		return userSvc.getUserByEmail(email);
	}



}
