package org.ukma.spring.crooodle.hotelsvc.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.HotelUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.entity.HotelEntity;
import org.ukma.spring.crooodle.hotelsvc.messaging.HotelMessage;
import org.ukma.spring.crooodle.hotelsvc.messaging.p2p.HotelProducer;
import org.ukma.spring.crooodle.hotelsvc.messaging.pubsub.HotelPublisher;
import org.ukma.spring.crooodle.hotelsvc.repository.HotelRepo;
import org.ukma.spring.crooodle.hotelsvc.repository.RoomRepo;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.hotelsvc.exception.EntityNotFoundException;
import org.ukma.spring.crooodle.hotelsvc.exception.ForbiddenException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class HotelSvc {

	private final HotelProducer hotelProducer;
	private final HotelPublisher hotelPublisher;
	private final UserClientSvc userSvc;
	private final HotelRepo repo;
	private final RoomRepo roomRepo;

	public UUID create(@NotNull HotelUpsertDto upsertDto) {
		if (!canCreate(upsertDto))
			throw new ForbiddenException("Only hotel owners can create hotels");

		var entity = HotelEntity.builder()
			.name(upsertDto.name())
			.address(upsertDto.address())
			.ownerId(userSvc.getCurrentUser().id())
			.build();
		entity = repo.saveAndFlush(entity);

		hotelProducer.sendHotelCreatedEvent(entity.getId());
		hotelPublisher.sendHotelCreatedEvent(entity.getId());
		return entity.getId();
	}

	// -------------- READ --------------
	public HotelResponseDto read(@NotNull UUID id) {
		return hotelEntityToDto(get(id));
	}

	HotelEntity get(@NotNull UUID id) {
		var hotel = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Hotel"));
		if (!canRead(hotel))
			throw new ForbiddenException("Cannot read hotel");

		return hotel;
	}

	public String readToHTML(@NotNull UUID id) {
		var dto = read(id);

		return "<!doctype html>\n" +
			"<html lang=\"en\">\n" +
			"<head>\n" +
			"  <meta charset=\"utf-8\">\n" +
			"  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
			"  <title>Hotel " + escapeHTML(dto.name()) + "</title>\n" +
			"  <style>\n" +
			"    body{font-family:Arial,Helvetica,sans-serif;padding:20px}\n" +
			"    .card{max-width:600px;border:1px solid #ddd;padding:16px;border-radius:8px}\n" +
			"    .row{margin:8px 0}\n" +
			"    .label{font-weight:600;color:#444;width:130px;display:inline-block}\n" +
			"  </style>\n" +
			"</head>\n" +
			"<body>\n" +
			"  <div class=\"card\">\n" +
			"    <h2>Hotel Details</h2>\n" +
			"    <div class=\"row\"><span class=\"label\">ID:</span>" +
			escapeHTML(dto.id().toString()) + "</div>\n" +
			"    <div class=\"row\"><span class=\"label\">Owner ID:</span>" +
			escapeHTML(dto.ownerId().toString()) + "</div>\n" +
			"    <div class=\"row\"><span class=\"label\">Name:</span>" +
			escapeHTML(dto.name()) + "</div>\n" +
			"    <div class=\"row\"><span class=\"label\">Address:</span>" +
			escapeHTML(dto.address()) + "</div>\n" +
			"    <div class=\"row\"><span class=\"label\">Room Count:</span>" +
			dto.roomCount() + "</div>\n" +
			"  </div>\n" +
			"</body>\n" +
			"</html>";
	}

	public byte[] readToCSV(@NotNull UUID id) {
		var dto = read(id);

		String csv = "id,ownerId,name,address,roomCount\n" +
			dto.id() + ',' +
			escapeCSV(dto.ownerId().toString()) + ',' +
			escapeCSV(dto.name()) + ',' +
			escapeCSV(dto.address()) + ',' +
			dto.roomCount() + '\n';

		return csv.getBytes(StandardCharsets.UTF_8);
	}

	// -------------- READ ALL --------------
	public List<HotelResponseDto> readAll() {
		return repo.findAll().stream()
			.map(this::hotelEntityToDto)
			.toList();
	}

	public String readAllToHTML() {
		var hotels = readAll();
		StringBuilder html = new StringBuilder();
		html.append("<!doctype html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Hotels</title>\n<style>")
			.append("body{font-family:Arial,Helvetica,sans-serif;padding:20px}")
			.append("table{border-collapse:collapse;width:100%;}")
			.append("th,td{border:1px solid #ccc;padding:8px;text-align:left}")
			.append("th{background:#f2f2f2}")
			.append("</style>\n</head>\n<body>\n<h2>Hotel List</h2>\n<table>\n<tr>")
			.append("<th>ID</th><th>Owner ID</th><th>Name</th><th>Address</th><th>Room Count</th></tr>\n");
		for (var h : hotels) {
			html.append("<tr>")
				.append("<td>").append(escapeHTML(h.id().toString())).append("</td>")
				.append("<td>").append(escapeHTML(h.ownerId().toString())).append("</td>")
				.append("<td>").append(escapeHTML(h.name())).append("</td>")
				.append("<td>").append(escapeHTML(h.address())).append("</td>")
				.append("<td>").append(h.roomCount()).append("</td>")
				.append("</tr>\n");
		}
		html.append("</table>\n</body>\n</html>");
		return html.toString();
	}

	public byte[] readAllToCSV() {
		var hotels = readAll();
		StringBuilder csv = new StringBuilder();
		csv.append("id,ownerId,name,address,roomCount\n");
		for (var h : hotels) {
			csv.append(h.id()).append(',')
				.append(escapeCSV(h.ownerId().toString())).append(',')
				.append(escapeCSV(h.name())).append(',')
				.append(escapeCSV(h.address())).append(',')
				.append(h.roomCount()).append('\n');
		}
		return csv.toString().getBytes(StandardCharsets.UTF_8);
	}

	// -------------- UPDATE --------------
	@Transactional
	public void update(@NotNull UUID id, @NotNull HotelUpsertDto upsertDto) {
		var entity = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Hotel"));
		if (!canUpdate(entity, upsertDto))
			throw new ForbiddenException("Cannot update hotel");

		entity.setName(upsertDto.name());
		entity.setAddress(upsertDto.address());
		repo.saveAndFlush(entity);

		hotelProducer.sendHotelUpdatedEvent(entity.getId());
		hotelPublisher.sendHotelUpdatedEvent(entity.getId());

	}

	// -------------- DELETE --------------
	@Transactional
	public void delete(@NotNull UUID id) {
		var hotel = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Hotel"));
		if (!canDelete(hotel))
			throw new ForbiddenException("Cannot delete Hotel");

		repo.deleteById(id);
		hotelProducer.sendHotelDeletedEvent(id);
		hotelPublisher.sendHotelDeletedEvent(id);
	}

	// -------------- MESSAGING --------------
	@JmsListener(destination = "hotel.topic", containerFactory = "hotelTopicListenerFactory")
	public void receiveFromTopic(HotelMessage msg) {
		log.info("{}-HOTEL SERVICE SUBSCRIBER: message received: {}", msg.getTimestamp(), msg);
	}

	// -------------- HELPERS --------------

	HotelResponseDto hotelEntityToDto(HotelEntity hotel) {
		var user = userSvc.getUser(hotel.getOwnerId());
		if (user == null) {
			throw new EntityNotFoundException(hotel.getOwnerId(), "User");
		}

		return HotelResponseDto.builder()
			.id(hotel.getId())
			.name(hotel.getName())
			.ownerId(hotel.getOwnerId())
			.ownerName(user.name())
			.address(hotel.getAddress())
			.build();
	}

	private boolean canCreate(HotelUpsertDto ignored_upsertDto) {
		return userSvc.getCurrentUserRole().equals(Role.ROLE_HOTEL_OWNER);
	}

	private boolean canUpdate(HotelEntity hotel, HotelUpsertDto ignored_upsertDto) {
		if (!userSvc.getCurrentUserRole().equals(Role.ROLE_HOTEL_OWNER))
			return false;

		return hotel.getOwnerId().equals(userSvc.getCurrentUser().id());
	}

	private boolean canRead(HotelEntity ignored_hotel) {
		return true;
	}

	@Retryable(backoff = @Backoff(delay = 2000))
	private boolean canDelete(HotelEntity hotel) {
		if (!userSvc.getCurrentUserRole().equals(Role.ROLE_HOTEL_OWNER))
			return false;

		return hotel.getOwnerId().equals(userSvc.getCurrentUser().id());
	}

	private String escapeHTML(String s) {
		if (s == null) return "";
		return s.replace("&", "&amp;")
			.replace("<", "&lt;")
			.replace(">", "&gt;")
			.replace("\"", "&quot;")
			.replace("'", "&#x27;");
	}

	private String escapeCSV(String s) {
		if (s == null) return "";
		boolean mustQuote = s.contains(",") || s.contains("\"") || s.contains("\n");
		String escaped = s.replace("\"", "\"\"");
		return mustQuote ? "\"" + escaped + "\"" : escaped;
	}

}
