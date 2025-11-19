package org.ukma.spring.crooodle.usersvc.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ukma.spring.crooodle.usersvc.controller.UserInternalController;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;
import org.ukma.spring.crooodle.usersvc.service.UserSvc;

import java.util.UUID;

import static org.mockito.Mockito.when;

public class UserContractBase {

	@Mock
	private UserSvc userSvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
		when(userSvc.getUserById(id)).thenReturn(UserResponseDto.builder()
			.id(id)
			.name("John Doe")
			.email("john.doe@example.com")
			.role(Role.ROLE_HOTEL_OWNER)
			.build());

		RestAssuredMockMvc.standaloneSetup(new UserInternalController(userSvc));
	}
}
