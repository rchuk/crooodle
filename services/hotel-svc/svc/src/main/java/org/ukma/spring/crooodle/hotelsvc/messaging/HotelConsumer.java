package org.ukma.spring.crooodle.hotelsvc.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class HotelConsumer {

	@JmsListener(destination = "${hotel.queue.name}")
	public void receiveMessage(HotelMessage event) {
		System.out.println("Received Hotel Event: " + event);
	}

}
