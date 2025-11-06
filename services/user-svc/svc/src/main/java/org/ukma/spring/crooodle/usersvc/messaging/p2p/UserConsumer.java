package org.ukma.spring.crooodle.usersvc.messaging.p2p;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.ukma.spring.crooodle.svc.messaging.ReservationMessage;

@Slf4j
@Component
public class UserConsumer {

	@JmsListener(
		destination = "${reservation.queue.name}",
		containerFactory = "userQueueListenerFactory",
		selector = "eventType = 'PENDING'")
	public void receiveRegisteredMessages(ReservationMessage event) {
		log.info("USER SERVICE CONSUMER-{}: A new reservation has been created. Status: {}", event.getTimestamp(), event.getEventType());
	}

	@JmsListener(
		destination = "${reservation.queue.name}",
		containerFactory = "userQueueListenerFactory",
		selector = "eventType = 'CONFIRMED'")
	public void receiveConfirmedMessages(ReservationMessage event) {
		log.info("USER SERVICE CONSUMER-{}: Reservation {} has been confirmed. Status: {}", event.getTimestamp(), event.getResId(), event.getEventType());
	}

	@JmsListener(
		destination = "${reservation.queue.name}",
		containerFactory = "userQueueListenerFactory",
		selector = "eventType = 'SETTLED'")
	public void receiveSettledMessages(ReservationMessage event) {
		log.info("USER SERVICE CONSUMER-{}: Reservation {} status has been changed to {}", event.getTimestamp(), event.getResId(), event.getEventType());
	}

	@JmsListener(
		destination = "${reservation.queue.name}",
		containerFactory = "userQueueListenerFactory",
		selector = "eventType = 'CANCELLED_BY_USER'")
	public void receiveCancelledByUserMessages(ReservationMessage event) {
		log.info("USER SERVICE CONSUMER-{}: Reservation {} has been cancelled by user", event.getTimestamp(), event.getResId());
	}

	@JmsListener(
		destination = "${reservation.queue.name}",
		containerFactory = "userQueueListenerFactory",
		selector = "eventType = 'CANCELLED_BY_HOTEL_OWNER'")
	public void receiveCancelledByOwnerMessages(ReservationMessage event) {
		log.info("USER SERVICE CONSUMER-{}: Reservation {} has been cancelled by hotel owner", event.getTimestamp(), event.getResId());
	}
}
