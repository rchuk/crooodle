package org.ukma.spring.crooodle.usersvc.messaging.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.svc.messaging.HotelMessage;

@Component
@Slf4j
public class UserSubscriber {

	@JmsListener(destination = "${hotel.topic.name}", containerFactory = "userTopicListenerFactory")
	public void receiveFromTopic(HotelMessage msg) {
		log.info("USER SERVICE SUBSCRIBER-{}: message received: {}", msg.getTimestamp(), msg);
	}
}

