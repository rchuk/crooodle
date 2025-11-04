package org.ukma.spring.crooodle.usersvc.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserConsumer {

	@JmsListener(destination = "${user.queue.name}")
	public void receiveMessage(UserMessage event) {
		log.info("USER CONSUMER: Received User Event: " + event);
	}

	@JmsListener(
		destination = "${user.queue.name}",
		selector = "eventType = 'created'")
	public void receiveCreatedMessages(UserMessage event) {
		log.info("USER CONSUMER: Received User Event: " + event);
	}

	@JmsListener(
		destination = "${user.queue.name}",
		selector = "eventType = 'update'")
	public void receiveUpdateMessages(UserMessage event) {
		log.info("USER CONSUMER: Received User Event: " + event);
	}

	@JmsListener(
		destination = "${user.queue.name}",
		selector = "eventType = 'removed'")
	public void receiveRemovedMessages(UserMessage event) {
		log.info("USER CONSUMER: Received User Event: " + event);
	}

}
