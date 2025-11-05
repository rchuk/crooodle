package org.ukma.spring.crooodle.hotelsvc.messaging;

import lombok.*;
import org.ukma.spring.crooodle.hotelsvc.messaging.HotelMessageType;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelMessage implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private UUID hotelId;
	private HotelMessageType eventType;
	private Instant timestamp;

}
