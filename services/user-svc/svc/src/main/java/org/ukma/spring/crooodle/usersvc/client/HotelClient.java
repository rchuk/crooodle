package org.ukma.spring.crooodle.usersvc.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
}
