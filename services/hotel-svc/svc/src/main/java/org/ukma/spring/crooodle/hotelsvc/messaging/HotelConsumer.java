package org.ukma.spring.crooodle.hotelsvc.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HotelConsumer {

	@JmsListener(
		destination = "${hotel.queue.name}",
	selector = "eventType = 'CREATED'")
	public void receiveCreatedMessages(HotelMessage event) {
		log.info("{}-HOTEL CONSUMER: Hotel {} was created.", event.getTimestamp(), event.getHotelId());
	}

	@JmsListener(
		destination = "${hotel.queue.name}",
		selector = "eventType = 'UPDATED'")
	public void receiveUpdateMessages(HotelMessage event) {
		log.info("{}-HOTEL CONSUMER: Hotel {} was updated.", event.getTimestamp(), event.getHotelId());
	}

	@JmsListener(
		destination = "${hotel.queue.name}",
		selector = "eventType = 'REMOVED'")
	public void receiveRemovedMessages(HotelMessage event) {
		log.info("{}-HOTEL CONSUMER: Hotel {} was removed.", event.getTimestamp(), event.getHotelId());
	}
}
