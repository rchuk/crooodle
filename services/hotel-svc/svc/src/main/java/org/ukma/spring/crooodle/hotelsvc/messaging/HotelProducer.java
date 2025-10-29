package org.ukma.spring.crooodle.hotelsvc.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HotelProducer {

	private final JmsTemplate jmsTemplate;

	@Value("${hotel.queue.name:hotel.events}")
	private String hotelQueueName;

	public void sendHotelCreatedEvent(UUID hotelId) {
		var event = HotelMessage.builder()
			.hotelId(hotelId)
			.eventType(MessageType.CREATED)
			.timestamp(Instant.now())
			.build();

		jmsTemplate.convertAndSend(hotelQueueName, event);
	}

	public void sendHotelUpdatedEvent(UUID hotelId) {
		var event = HotelMessage.builder()
			.hotelId(hotelId)
			.eventType(MessageType.UPDATED)
			.timestamp(Instant.now())
			.build();

		jmsTemplate.convertAndSend(hotelQueueName, event);
	}

	public void sendHotelDeletedEvent(UUID hotelId) {
		var event = HotelMessage.builder()
			.hotelId(hotelId)
			.eventType(MessageType.REMOVED)
			.timestamp(Instant.now())
			.build();

		jmsTemplate.convertAndSend(hotelQueueName, event);
	}
}
