package org.ukma.spring.crooodle.usersvc.messaging;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;


@Builder
@Setter
@Getter
@Data
public class UserMessage implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String email;
	private MessageType eventType;
	private Instant timestamp;

	@Override
	public String toString() {
		return String.format(
			"UserMessage{email=%s, eventType=%s, timestamp=%s}",
			email, eventType, timestamp
		);
	}
}
