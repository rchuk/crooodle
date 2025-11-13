package org.ukma.spring.crooodle.hotelsvc.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomResponseDto;
import org.ukma.spring.crooodle.hotelsvc.dto.RoomUpsertDto;
import org.ukma.spring.crooodle.hotelsvc.entity.RoomEntity;
import org.ukma.spring.crooodle.hotelsvc.repository.RoomRepo;
import org.ukma.spring.crooodle.usersvc.dto.UserRole;
import org.ukma.spring.crooodle.usersvc.client.UserSvcClient;
import org.ukma.spring.crooodle.hotelsvc.exception.EntityNotFoundException;
import org.ukma.spring.crooodle.hotelsvc.exception.ForbiddenException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoomSvc {
    private final UserSvcClient userSvc;
    private final RoomTypeSvc roomTypeSvc;
    private final HotelSvc hotelSvc;
    private final RoomRepo roomRepo;

    private final ApplicationEventPublisher eventPub;

    @Transactional
    public UUID create(UUID hotelId, RoomUpsertDto requestDto) {
        if (!canCreate(hotelId))
            throw new ForbiddenException("Can't create hotel");

        var roomType = roomTypeSvc.get(requestDto.roomTypeId());
        var room = RoomEntity.builder()
            .name(requestDto.name())
            .type(roomType)
            .build();
        room = roomRepo.saveAndFlush(room);

        return room.getId();
    }

		// -------------- READ --------------
    public RoomResponseDto read(@NotNull UUID roomId) {
        var room = get(roomId);

        return RoomResponseDto.builder()
            .id(room.getId())
            .type(roomTypeSvc.roomTypeEntityToDto(room.getType()))
            .name(room.getName())
            .build();
    }

	public String readToHTML(@NotNull UUID roomId) {
		var room = read(roomId);
		StringBuilder html = new StringBuilder();
		html.append("<!doctype html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n")
			.append("<title>Room ").append(escapeHtml(room.id().toString())).append("</title>\n<style>")
			.append("body{font-family:Arial,Helvetica,sans-serif;padding:20px}")
			.append(".card{max-width:600px;border:1px solid #ddd;padding:16px;border-radius:8px}")
			.append(".row{margin:8px 0}.label{font-weight:600;color:#444;width:110px;display:inline-block}")
			.append("</style>\n</head>\n<body>\n<div class=\"card\">\n<h2>Room details</h2>\n")
			.append("<div class=\"row\"><span class=\"label\">ID:</span>")
			.append(escapeHtml(room.id().toString())).append("</div>\n")
			.append("<div class=\"row\"><span class=\"label\">Name:</span>")
			.append(escapeHtml(room.name())).append("</div>\n")
			.append("<div class=\"row\"><span class=\"label\">Type:</span>")
			.append(escapeHtml(room.type().toString())).append("</div>\n")
			.append("</div>\n</body>\n</html>");
		return html.toString();
	}

	public byte[] readToCSV(@NotNull UUID roomId) {
		var room = read(roomId);
		StringBuilder csv = new StringBuilder();
		csv.append("id,type,name\n")
			.append(room.id()).append(',')
			.append(escapeCsv(room.type().toString())).append(',')
			.append(escapeCsv(room.name())).append('\n');
		return csv.toString().getBytes(StandardCharsets.UTF_8);
	}

    RoomEntity get(@NotNull UUID roomId) {
        return roomRepo.findById(roomId).orElseThrow(() -> new EntityNotFoundException(roomId, "Room"));
    }

	// -------------- READ ALL BY HOTEL --------------
    public List<RoomResponseDto> readAllByHotel(@NotNull UUID hotelId) {
        // TODO: Add pagination
        var hotel = hotelSvc.get(hotelId);

        return roomRepo.findAllByType_Hotel(hotel)
            .stream()
            .map(this::roomEntityToDto)
            .toList();
    }

	public String readAllByHotelToHTML(@NotNull UUID hotelId) {
		var rooms = readAllByHotel(hotelId);
		StringBuilder html = new StringBuilder();
		html.append("<!doctype html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n")
			.append("<title>Rooms for Hotel ").append(escapeHtml(hotelId.toString())).append("</title>\n<style>")
			.append("body{font-family:Arial,Helvetica,sans-serif;padding:20px}")
			.append("table{border-collapse:collapse;width:100%;}")
			.append("th,td{border:1px solid #ccc;padding:8px;text-align:left}")
			.append("th{background:#f2f2f2}")
			.append("</style>\n</head>\n<body>\n<h2>Room List</h2>\n<table>\n<tr>")
			.append("<th>ID</th><th>Name</th><th>Type</th></tr>\n");
		return getString(rooms, html);
	}

	public byte[] readAllByHotelToCSV(@NotNull UUID hotelId) {
		var rooms = readAllByHotel(hotelId);
		return getBytes(rooms);
	}

	// -------------- READ ALL BY TYPE --------------
	public List<RoomResponseDto> readAllByType(@NotNull UUID typeId) {
        var type = roomTypeSvc.get(typeId);

        return roomRepo.findAllByType(type)
            .stream()
            .map(this::roomEntityToDto)
            .toList();
    }

	public String readAllByTypeToHTML(@NotNull UUID typeId) {
		var rooms = readAllByType(typeId);
		StringBuilder html = new StringBuilder();
		html.append("<!doctype html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n")
			.append("<title>Rooms for Type ").append(escapeHtml(typeId.toString())).append("</title>\n<style>")
			.append("body{font-family:Arial,Helvetica,sans-serif;padding:20px}")
			.append("table{border-collapse:collapse;width:100%;}")
			.append("th,td{border:1px solid #ccc;padding:8px;text-align:left}")
			.append("th{background:#f2f2f2}")
			.append("</style>\n</head>\n<body>\n<h2>Room List</h2>\n<table>\n<tr>")
			.append("<th>ID</th><th>Name</th><th>Type</th></tr>\n");
		return getString(rooms, html);
	}

	public byte[] readAllByTypeToCSV(@NotNull UUID typeId) {
		var rooms = readAllByType(typeId);
		return getBytes(rooms);
	}

	// -------------- READ ALL BY HOTEL AND TYPE --------------
	public List<RoomResponseDto> readAllByHotelAndType(@NotNull UUID hotelId, @NotNull UUID typeId) {
		var type = roomTypeSvc.get(typeId);
		var hotel = hotelSvc.get(hotelId);

		return roomRepo.findAllByType_HotelIdAndType(hotel.getId(), type)
			.stream()
			.map(this::roomEntityToDto)
			.toList();
	}

	public String readAllByHotelAndTypeAsHtml(@NotNull UUID hotelId, @NotNull UUID typeId) {
		var rooms = readAllByHotelAndType(hotelId, typeId);
		StringBuilder html = new StringBuilder();
		html.append("<!doctype html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n")
			.append("<title>Rooms for Hotel ").append(escapeHtml(hotelId.toString()))
			.append(" and Type ").append(escapeHtml(typeId.toString())).append("</title>\n<style>")
			.append("body{font-family:Arial,Helvetica,sans-serif;padding:20px}")
			.append("table{border-collapse:collapse;width:100%;}")
			.append("th,td{border:1px solid #ccc;padding:8px;text-align:left}")
			.append("th{background:#f2f2f2}")
			.append("</style>\n</head>\n<body>\n<h2>Room List</h2>\n<table>\n<tr>")
			.append("<th>ID</th><th>Name</th><th>Type</th></tr>\n");
		return getString(rooms, html);
	}

	public byte[] readAllByHotelAndTypeAsCsv(@NotNull UUID hotelId, @NotNull UUID typeId) {
		var rooms = readAllByHotelAndType(hotelId, typeId);
		return getBytes(rooms);
	}

	// -------------- UPDATE --------------
	@Transactional
    public void update(@NotNull UUID roomId, @NotNull RoomResponseDto roomDto) {
        var roomType = roomTypeSvc.get(roomId);
        var room = get(roomId);
        if (!canUpdate(room))
            throw new ForbiddenException("Can't update room");

        room.setName(roomDto.name());
        room.setType(roomType);
        roomRepo.saveAndFlush(room);
    }

	// -------------- DELETE --------------
    @Transactional
    public void delete(@NotNull UUID roomId) {
        var room = get(roomId);
        if (!canDelete(room))
            throw new ForbiddenException("Can't delete room");

        roomRepo.deleteById(roomId);
    }

	// -------------- HELPERS --------------
    public RoomResponseDto roomEntityToDto(RoomEntity entity) {
        return RoomResponseDto.builder()
            .id(entity.getId())
            .type(roomTypeSvc.roomTypeEntityToDto(entity.getType()))
            .name(entity.getName())
            .build();
    }

    private boolean canCreate(UUID hotelId) {
        return userSvc.getCurrentUserRole().equals(UserRole.ROLE_HOTEL_OWNER);
    }

    private boolean canUpdate(RoomEntity room) {
        return userSvc.getCurrentUser().id().equals(room.getType().getHotel().getOwnerId());
    }

    private boolean canDelete(RoomEntity room) {
        return userSvc.getCurrentUser().id().equals(room.getType().getHotel().getOwnerId());
    }


	private String escapeHtml(String s) {
		if (s == null) return "";
		return s.replace("&", "&amp;")
			.replace("<", "&lt;")
			.replace(">", "&gt;")
			.replace("\"", "&quot;")
			.replace("'", "&#x27;");
	}

	private String escapeCsv(String s) {
		if (s == null) return "";
		boolean mustQuote = s.contains(",") || s.contains("\"") || s.contains("\n");
		String escaped = s.replace("\"", "\"\"");
		return mustQuote ? "\"" + escaped + "\"" : escaped;
	}

	private byte[] getBytes(List<RoomResponseDto> rooms) {
		StringBuilder csv = new StringBuilder();
		csv.append("id,name,type\n");
		for (var r : rooms) {
			csv.append(r.id()).append(',')
				.append(escapeCsv(r.name())).append(',')
				.append(escapeCsv(r.type().toString())).append('\n');
		}
		return csv.toString().getBytes(StandardCharsets.UTF_8);
	}

	private String getString(List<RoomResponseDto> rooms, StringBuilder html) {
		for (var r : rooms) {
			html.append("<tr>")
				.append("<td>").append(escapeHtml(r.id().toString())).append("</td>")
				.append("<td>").append(escapeHtml(r.name())).append("</td>")
				.append("<td>").append(escapeHtml(r.type().toString())).append("</td>")
				.append("</tr>\n");
		}
		html.append("</table>\n</body>\n</html>");
		return html.toString();
	}

}
