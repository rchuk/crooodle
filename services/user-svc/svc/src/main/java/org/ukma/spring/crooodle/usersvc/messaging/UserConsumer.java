package org.ukma.spring.crooodle.usersvc.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserConsumer {

	@JmsListener(
		destination = "${hotel.queue.name}",
		selector = "eventType = 'REGISTERED'")
	public void receiveRegisteredMessages(UserMessage event) {
		log.info("{}-user CONSUMER: A new user is registered by {}", event.getTimestamp(), event.getEmail());
	}

	@JmsListener(
		destination = "${user.queue.name}",
		selector = "eventType = 'LOGGED_IN'")
	public void receiveLoggedInMessages(UserMessage event) {
		log.info("{}-USER CONSUMER: User {} is logged in", event.getTimestamp(), event.getEmail());
	}

	@JmsListener(
		destination = "${user.queue.name}",
		selector = "eventType = 'LOGGED_OUT'")
	public void receiveLoggedOutMessages(UserMessage event) {
		log.info("{}-USER CONSUMER: User {} is logged out", event.getTimestamp(), event.getEmail());
	}

}
