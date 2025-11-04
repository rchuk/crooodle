package org.ukma.spring.crooodle.hotelsvc.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
public class HotelProducer {

	private final JmsTemplate jmsTemplate;

	@Value("${hotel.queue.name:hotel.events}")
	private String hotelQueueName;

	public HotelProducer(@Qualifier("hotelJmsTemplate") JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendHotelCreatedEvent(UUID hotelId) {
		sendEvent(hotelId, HotelMessageType.CREATED);
	}

	public void sendHotelUpdatedEvent(UUID hotelId) {
		sendEvent(hotelId, HotelMessageType.UPDATED);
	}

	public void sendHotelDeletedEvent(UUID hotelId) {
		sendEvent(hotelId, HotelMessageType.REMOVED);
	}

	private void sendEvent(UUID hotelId, HotelMessageType type) {
		var event = HotelMessage.builder()
			.hotelId(hotelId)
			.eventType(type)
			.timestamp(Instant.now())
			.build();

		log.info("HOTEL PRODUCER: Sending JMS message to queue '{}': {}", hotelQueueName, event);

		jmsTemplate.convertAndSend(hotelQueueName, event, message -> {
			message.setStringProperty("eventType", type.name());
			String date = event.getTimestamp()
				.atZone(ZoneOffset.UTC)
				.toLocalDate()
				.format(DateTimeFormatter.ISO_LOCAL_DATE);

			message.setStringProperty("date", date);
			message.setStringProperty("eventType", type.name());
			return message;
		});
	}
}
