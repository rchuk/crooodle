package org.ukma.spring.crooodle.hotelsvc.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HotelMessageType {
	@JsonProperty("created")
	CREATED,
	@JsonProperty("updated")
	UPDATED,
	@JsonProperty("removed")
	REMOVED
}
