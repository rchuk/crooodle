package org.ukma.spring.crooodle.reservationsvc.messaging.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.svc.messaging.HotelMessage;

@Slf4j
@Component
public class ResSubscriber {

	@JmsListener(destination = "${hotel.topic.name}", containerFactory = "resTopicListenerFactory")
	public void receiveFromTopic(HotelMessage msg) {
		log.info("RESERVATION SERVICE SUBSCRIBER-{}: message received: {}", msg.getTimestamp(), msg);
	}
}
