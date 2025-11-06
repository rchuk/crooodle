package org.ukma.spring.crooodle.svc.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationMessage implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private UUID resId;
//	private UUID userId;
	private ReservationMessageType eventType;
	private Instant timestamp;
}
