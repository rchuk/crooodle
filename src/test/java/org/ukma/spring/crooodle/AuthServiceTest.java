package org.ukma.spring.crooodle;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.service.AuthService;

import static org.ukma.spring.crooodle.UtilsForTests.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthServiceTest extends BaseTest {

    @Autowired
    private AuthService authService;

    private final Faker faker = new Faker();

    @Autowired
    public AuthServiceTest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @Test
    void testRegistration() {
        UserRegisterRequestDto userRegisterRequestDto = getRandomUserDto();
        AccessTokenResponseDto accessTokenResponseDto = authService.register(userRegisterRequestDto);

        assertNotNull(accessTokenResponseDto.getAccessToken(), "Access token має бути створений");
        assertFalse(accessTokenResponseDto.getAccessToken().isEmpty(), "Access token не має бути порожнім");

        String accessToken = accessTokenResponseDto.getAccessToken();
        assertEquals(3, accessToken.split("\\.").length, "Access token має відповідати формату JWT");
    }

    @Test
    void testLogin() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        createUser(email, password);

        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setEmail(email);
        userLoginRequestDto.setPassword(password);

        AccessTokenResponseDto accessTokenResponseDto = authService.login(userLoginRequestDto);

        assertNotNull(accessTokenResponseDto.getAccessToken(), "Access token має бути створений");
        assertFalse(accessTokenResponseDto.getAccessToken().isEmpty(), "Access token не має бути порожнім");

        String accessToken = accessTokenResponseDto.getAccessToken();
        assertEquals(3, accessToken.split("\\.").length, "Access token має відповідати формату JWT");
    }
}