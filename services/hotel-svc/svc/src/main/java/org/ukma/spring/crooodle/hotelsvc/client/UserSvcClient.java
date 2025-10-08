package org.ukma.spring.crooodle.hotelsvc.client;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserSvcClient {

	private final RestClient rc;
	private final String apiKey;

	public UserSvcClient(@Value("${usersvc.base-url}") String baseUrl,
											 @Value("${usersvc.api-key}") String apiKey) {
		this.apiKey = apiKey;
		this.rc = RestClient.builder().baseUrl(baseUrl).build();
	}

	@Retry(name = "usersvc")
	public String internalTest() {
		return rc.get()
			.uri("/internal/api/test")
			.header("X-API-KEY", apiKey)
			.accept(MediaType.TEXT_PLAIN)
			.retrieve()
			.body(String.class);
	}
}
