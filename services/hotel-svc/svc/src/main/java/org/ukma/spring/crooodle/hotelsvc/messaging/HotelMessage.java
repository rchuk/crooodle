package org.ukma.spring.crooodle.hotelsvc.messaging;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Builder
@Setter
@Getter
@Data
public class HotelMessage implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;


	private UUID hotelId;
	private MessageType eventType;
	private Instant timestamp;

	@Override
	public String toString() {
		return String.format(
			"HotelMessage{hotelId=%s, eventType=%s, timestamp=%s}",
			hotelId, eventType, timestamp
		);
	}
}
