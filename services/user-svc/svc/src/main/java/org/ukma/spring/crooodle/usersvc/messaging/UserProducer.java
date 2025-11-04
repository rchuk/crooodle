package org.ukma.spring.crooodle.usersvc.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class UserProducer {

	private final JmsTemplate jmsTemplate;

	@Value("${user.queue.name:user.events}")
	private String userQueueName;

	public UserProducer(@Qualifier("userJmsTemplate") JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendLoggedInEvent(String email) {
		sendEvent(email, UserMessageType.LOGGED_IN);
	}

	public void sendLoggedOutEvent(String email) {
		sendEvent(email, UserMessageType.LOGGED_OUT);
	}

	public void sendRegisteredEvent(String email) {
		sendEvent(email, UserMessageType.REGISTERED);
	}

	private void sendEvent(String email, UserMessageType type) {
		var event = UserMessage.builder()
			.email(email)
			.eventType(type)
			.timestamp(Instant.now())
			.build();

		log.info("USER PRODUCER: Sending JMS message to queue '{}': {}", userQueueName, event);

		jmsTemplate.convertAndSend(userQueueName, event, message -> {
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
