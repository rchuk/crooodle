package org.ukma.spring.crooodle.usersvc.messaging.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
//import org.ukma.spring.crooodle.hotelsvc.messaging.HotelMessage;

@Component
@Slf4j
public class UserSubscriber {
//	@JmsListener(destination = "hotel.topic", containerFactory = "hotelTopicListenerFactory")
//	public void receiveFromTopic(HotelMessage msg) {
//		log.info("HOTEL SUBSCRIBER-{}: message received: {}", msg.getTimestamp(), msg);
//	}
}
	
