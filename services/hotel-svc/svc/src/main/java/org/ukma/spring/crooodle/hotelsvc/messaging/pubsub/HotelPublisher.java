package org.ukma.spring.crooodle.hotelsvc.messaging.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.svc.messaging.HotelMessage;
import org.ukma.spring.crooodle.svc.messaging.HotelMessageType;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
public class HotelPublisher {
	private final JmsTemplate jmsTemplate;

	@Value("${hotel.topic.name:hotel.topic}")
	private String hotelTopic;

	public HotelPublisher(@Qualifier("pubSubHotelJmsTemplate") JmsTemplate jmsTemplate) {
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

		log.info("HOTEL PUBLISHER: Sending JMS message to topic '{}': {}", hotelTopic, event);

		jmsTemplate.convertAndSend(hotelTopic, event, message -> {
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
