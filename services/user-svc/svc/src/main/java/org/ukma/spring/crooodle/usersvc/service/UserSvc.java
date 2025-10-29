package org.ukma.spring.crooodle.usersvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.dto.UserRegisterDto;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;
import org.ukma.spring.crooodle.usersvc.messaging.UserProducer;
import org.ukma.spring.crooodle.usersvc.repository.RoleRepo;
import org.ukma.spring.crooodle.usersvc.entity.UserEntity;
import org.ukma.spring.crooodle.usersvc.repository.UserRepo;
import org.ukma.spring.crooodle.usersvc.exception.EntityNotFoundException;
import org.ukma.spring.crooodle.usersvc.exception.InvalidRequestException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserSvc implements UserDetailsService {
    private final UserRepo repo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
		private final UserProducer userProducer;

	// -------------- AUTHENTICATION --------------
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Transactional
    public UserResponseDto register(UserRegisterDto dto) {
        if (repo.existsByEmail(dto.email()))
            throw new InvalidRequestException("User with the following email already exists");

        var role = switch (dto.role()) {
            case TRAVELER -> Role.ROLE_TRAVELER;
            case HOTEL_OWNER -> Role.ROLE_HOTEL_OWNER;
        };

        var defaultRole = roleRepo.findByRole(role).orElseThrow();
        var entity = UserEntity.builder()
            .name(dto.name())
            .email(dto.email())
            .passwordHash(passwordEncoder.encode(dto.password()))
            .role(defaultRole)
            .build();
        entity = repo.saveAndFlush(entity);

				userProducer.sendRegisteredEvent(entity.getEmail());
        return userEntityToDto(entity);
    }

    public Role getCurrentUserRole() {
        try {
            return getCurrentUser().role();
        } catch (Exception e) {
            return Role.ROLE_ANONYMOUS;
        }
    }

    public UserResponseDto getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated())
            return null;

        if (auth.getPrincipal() instanceof UserEntity entity)
            return userEntityToDto(entity);

        return null;
    }

	// -------------- GET ALL --------------
	public List<UserResponseDto> getAllUsers() {

		return repo.findAll()
			.stream()
			.map(this::userEntityToDto)
			.toList();
	}

	public String getAllToHTML() {
		var users = getAllUsers();
		StringBuilder html = new StringBuilder();
		html.append("<!doctype html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>Users</title>\n<style>")
			.append("body{font-family:Arial,Helvetica,sans-serif;padding:20px}")
			.append("table{border-collapse:collapse;width:100%;}")
			.append("th,td{border:1px solid #ccc;padding:8px;text-align:left}")
			.append("th{background:#f2f2f2}")
			.append("</style>\n</head>\n<body>\n<h2>User List</h2>\n<table>\n<tr>")
			.append("<th>ID</th><th>Name</th><th>Email</th><th>Role</th></tr>\n");
		for (var u : users) {
			html.append("<tr>")
				.append("<td>").append(escapeHTML(u.id().toString())).append("</td>")
				.append("<td>").append(escapeHTML(u.name())).append("</td>")
				.append("<td>").append(escapeHTML(u.email())).append("</td>")
				.append("<td>").append(escapeHTML(u.role().toString())).append("</td>")
				.append("</tr>\n");
		}
		html.append("</table>\n</body>\n</html>");
		return html.toString();
	}

	public byte[] getAllToCSV() {
		var users = getAllUsers();
		StringBuilder csv = new StringBuilder();
		csv.append("id,name,email,role\n");
		for (var u : users) {
			csv.append(u.id()).append(',')
				.append(escapeCSV(u.name())).append(',')
				.append(escapeCSV(u.email())).append(',')
				.append(escapeCSV(u.role().toString())).append('\n');
		}
		return csv.toString().getBytes(StandardCharsets.UTF_8);
	}

	public UserResponseDto getUserByEmail(String email) {
		var entity = repo.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(email, "User"));

		return userEntityToDto(entity);
	}

	// -------------- GET BY ID --------------
	public UserResponseDto getUserById(UUID id) {
        var entity = repo.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "User"));

        return userEntityToDto(entity);
	}

	public String getUserByIdToHTML(UUID id) {
		var dto = getUserById(id);

        return "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n" +
                "  <title>User " + escapeHTML(dto.id().toString()) + "</title>\n" +
                "  <style>\n" +
                "    body{font-family:Arial,Helvetica,sans-serif;padding:20px}\n" +
                "    .card{max-width:600px;border:1px solid #ddd;padding:16px;border-radius:8px}\n" +
                "    .row{margin:8px 0}\n" +
                "    .label{font-weight:600;color:#444;width:110px;display:inline-block}\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"card\">\n" +
                "    <h2>User details</h2>\n" +
                "    <div class=\"row\"><span class=\"label\">ID:</span>" +
                escapeHTML(dto.id().toString()) + "</div>\n" +
                "    <div class=\"row\"><span class=\"label\">Name:</span>" +
                escapeHTML(dto.name()) + "</div>\n" +
                "    <div class=\"row\"><span class=\"label\">Email:</span>" +
                escapeHTML(dto.email()) + "</div>\n" +
                "    <div class=\"row\"><span class=\"label\">Role:</span>" +
                escapeHTML(dto.role().toString()) + "</div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>";
	}

	// -------------- HELPERS --------------
	private String escapeHTML(String s) {
		if (s == null) return "";
		return s.replace("&", "&amp;")
			.replace("<", "&lt;")
			.replace(">", "&gt;")
			.replace("\"", "&quot;")
			.replace("'", "&#x27;");
	}

	public byte[] getUserByIdAsCSV(UUID id) {
		var dto = getUserById(id);

        String csv = "id,name,email,role\n" +
                dto.id() + ',' +
                escapeCSV(dto.name()) + ',' +
                escapeCSV(dto.email()) + ',' +
                escapeCSV(dto.role().toString()) + '\n';

		return csv.getBytes(StandardCharsets.UTF_8);
	}

	private String escapeCSV(String value) {
		if (value == null) return "";

		boolean mustQuote = value.contains(",") || value.contains("\"") || value.contains("\n");
		String escaped = value.replace("\"", "\"\"");
		return mustQuote ? "\"" + escaped + "\"" : escaped;
	}

	public UserResponseDto userEntityToDto(UserEntity entity) {
        return UserResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .email(entity.getEmail())
            .role(entity.getRole().getRole())
            .build();
	}
}
