package org.ukma.spring.crooodle.svc.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReservationMessageType {

	@JsonProperty("PENDING")
	PENDING,
	@JsonProperty("CONFIRMED")
	CONFIRMED,
	@JsonProperty("SETTLED")
	SETTLED,
	@JsonProperty("CANCELLED_BY_USER")
	CANCELLED_BY_USER,
	@JsonProperty("CANCELLED_BY_HOTEL_OWNER")
	CANCELLED_BY_HOTEL_OWNER
}
