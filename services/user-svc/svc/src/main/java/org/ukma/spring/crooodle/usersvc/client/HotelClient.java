package org.ukma.spring.crooodle.usersvc.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class HotelClient {

	public record AvailabilityDto(long roomId, boolean available) {}

	private final RestClient rc;
	private final String apiKey;

	public HotelClient(@Value("${hotelsvc.base-url}") String baseUrl,
										 @Value("${hotelsvc.api-key}") String apiKey) {
		this.apiKey = apiKey;
		this.rc = RestClient.builder().baseUrl(baseUrl).build();
	}

	@Retry(name = "hotelsvc")
	public AvailabilityDto getAvailabilityInternal(long roomId) {
		return rc.get()
			.uri("/internal/api/rooms/{id}/availability", roomId)
			.header("X-API-KEY", apiKey)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.body(AvailabilityDto.class);
	}

	@Retry(name = "hotelsvc")
	public List<Map<String, Object>> getRooms() {
		return rc.get()
			.uri("/api/rooms")
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.body(new ParameterizedTypeReference<>() {});
	}

	@Retry(name = "hotelsvc")
	public String getRoomsHtml() {
		return rc.get()
			.uri("/api/rooms/html")
			.accept(MediaType.TEXT_HTML)
			.retrieve()
			.body(String.class);
	}

	@Retry(name = "hotelsvc")
	public byte[] exportRoomsCsv() {
		return rc.get()
			.uri("/api/rooms/export")
			.accept(MediaType.valueOf("text/csv"))
			.retrieve()
			.body(byte[].class);
	}
}
