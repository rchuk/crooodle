package org.ukma.spring.crooodle.usersvc.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UserProducer {

	private final JmsTemplate jmsTemplate;

	@Value("${user.queue.name:user.events}")
	private String userQueueName;

	public void sendLoggedInEvent(String email) {
		var event = UserMessage.builder()
			.email(email)
			.eventType(MessageType.LOGGED_IN)
			.timestamp(Instant.now())
			.build();

		jmsTemplate.convertAndSend(userQueueName, event);
	}

	public void sendLoggedOutEvent(String email) {
		var event = UserMessage.builder()
			.email(email)
			.eventType(MessageType.LOGGED_OUT)
			.timestamp(Instant.now())
			.build();

		jmsTemplate.convertAndSend(userQueueName, event);
	}

	public void sendRegisteredEvent(String email) {
		var event = UserMessage.builder()
			.email(email)
			.eventType(MessageType.REGISTERED)
			.timestamp(Instant.now())
			.build();

		jmsTemplate.convertAndSend(userQueueName, event);
	}
}
