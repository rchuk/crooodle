package org.ukma.spring.crooodle.usersvc.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

	@JmsListener(destination = "${user.queue.name}")
	public void receiveMessage(UserMessage event) {
		System.out.println("Received User Event: " + event);
	}

}
