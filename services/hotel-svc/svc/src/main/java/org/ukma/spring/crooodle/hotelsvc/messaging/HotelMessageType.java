package org.ukma.spring.crooodle.hotelsvc.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HotelMessageType {
	@JsonProperty("CREATED")
	CREATED,
	@JsonProperty("UPDATED")
	UPDATED,
	@JsonProperty("REMOVED")
	REMOVED
}
