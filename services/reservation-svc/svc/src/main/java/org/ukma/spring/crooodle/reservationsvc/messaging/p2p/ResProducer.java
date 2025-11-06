package org.ukma.spring.crooodle.reservationsvc.messaging.p2p;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.svc.messaging.ReservationMessage;
import org.ukma.spring.crooodle.svc.messaging.ReservationMessageType;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@Slf4j
public class ResProducer {

	@Value("${reservation.queue.name:reservation.events}")
	private String resQueue;
	private final JmsTemplate jmsTemplate;

	public ResProducer(@Qualifier("p2pResJmsTemplate") JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendPendingEvent(UUID resId) {
		sendEvent(resId, ReservationMessageType.PENDING);
	}

	public void sendConfirmedEvent(UUID resId) {
		sendEvent(resId, ReservationMessageType.CONFIRMED);
	}

	public void sendSettledEvent(UUID resId) {
		sendEvent(resId, ReservationMessageType.SETTLED);
	}

	public void sendCancelledEvent(UUID resId, ReservationMessageType type) {
		sendEvent(resId, type);
	}

	private void sendEvent(UUID resId, ReservationMessageType type) {
		var event = ReservationMessage.builder()
			.resId(resId)
			.eventType(type)
			.timestamp(Instant.now())
			.build();

		log.info("RESERVATION PRODUCER: Sending JMS message to queue '{}': {}", resQueue, event);

		jmsTemplate.convertAndSend(resQueue, event, message -> {
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
