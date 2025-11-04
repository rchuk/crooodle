package org.ukma.spring.crooodle.usersvc.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserMessageType {
	@JsonProperty("logged_in")
	LOGGED_IN,
	@JsonProperty("logged_out")
	LOGGED_OUT,
	@JsonProperty("registered")
	REGISTERED
}
