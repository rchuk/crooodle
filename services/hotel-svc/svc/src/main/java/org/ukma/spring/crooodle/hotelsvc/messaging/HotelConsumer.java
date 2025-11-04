package org.ukma.spring.crooodle.hotelsvc.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HotelConsumer {

	@JmsListener(
		destination = "${hotel.queue.name}")
	public void receiveMessage(HotelMessage event) {
		log.info("HOTEL CONSUMER: Received Hotel Event: " + event);
	}

	@JmsListener(
		destination = "${hotel.queue.name}",
	selector = "eventType = 'created'")
	public void receiveCreatedMessages(HotelMessage event) {
		log.info("HOTEL CONSUMER: Received Hotel Event: " + event);
	}

	@JmsListener(
		destination = "${hotel.queue.name}",
		selector = "eventType = 'update'")
	public void receiveUpdateMessages(HotelMessage event) {
		log.info("HOTEL CONSUMER: Received Hotel Event: " + event);
	}

	@JmsListener(
		destination = "${hotel.queue.name}",
		selector = "eventType = 'removed'")
	public void receiveRemovedMessages(HotelMessage event) {
		log.info("HOTEL CONSUMER: Received Hotel Event: " + event);
	}
}
