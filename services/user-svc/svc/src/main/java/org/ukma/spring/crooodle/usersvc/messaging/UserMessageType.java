package org.ukma.spring.crooodle.usersvc.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserMessageType {
	@JsonProperty("LOGGED_IN")
	LOGGED_IN,
	@JsonProperty("LOGGED_OUT")
	LOGGED_OUT,
	@JsonProperty("REGISTERED")
	REGISTERED
}
