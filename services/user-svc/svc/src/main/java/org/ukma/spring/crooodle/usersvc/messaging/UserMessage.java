package org.ukma.spring.crooodle.usersvc.messaging;

import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMessage implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String email;
	private UserMessageType eventType;
	private Instant timestamp;
}
