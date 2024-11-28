package org.ukma.spring.crooodle.components.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.entities.UserEntity;
import org.ukma.spring.crooodle.exception.PublicBadRequestException;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.repository.UserRepository;
import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.impl.AuthServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * Component (unit) tests for the {@link AuthServiceImpl} class
 *
 *
 * register_success:
 * Tests successful user registration with valid creds
 *
 *
 * register_userAlreadyExists:
 * Error scenario - register a user that already exists -> PublicBadRequestException
 *
 *
 * login_success:
 * Tests successful login with valid creds
 *
 *
 * login_invalidCredentials:
 * Error scenario - invalid credentials (wrong email or password) -> PublicBadRequestException
 *
 *
 * login_userNotFound:
 * Error scenario - the user does not exist in the database -> PublicNotFoundException
 *
 */


class AuthServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private UserEntity testUser;
    private UserRegisterRequestDto registerRequestDto;
    private UserLoginRequestDto loginRequestDto;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        testUser = UserEntity
            .builder()
            .id(1L)
            .name("testUser")
            .email("test@example.com")
            .passwordHash("hashedPassword")
            .build();

        registerRequestDto = UserRegisterRequestDto
            .builder()
            .name("testUser")
            .email("test@example.com")
            .password("password")
            .build();

        loginRequestDto = UserLoginRequestDto
            .builder()
            .email("test@example.com")
            .password("password")
            .build();


    }

    @Test
    void register_success() {

        when(
            userRepository
            .existsByEmail(registerRequestDto.getEmail())
        )
        .thenReturn(false);

        when(
            passwordEncoder
            .encode(registerRequestDto.getPassword())
        )
        .thenReturn("hashedPassword");

        when(
            userRepository
            .saveAndFlush(any(UserEntity.class))
        )
        .thenReturn(testUser);

        when(
            jwtService
            .generateToken(any(UserEntity.class))
        )
        .thenReturn("jwtToken");



        AccessTokenResponseDto response = authService
                                                .register(registerRequestDto);


        assertNotNull(response);

        assertEquals(
            "jwtToken",
            response.getAccessToken()
        );

        verify(
            userRepository
        )
        .saveAndFlush(
            any(UserEntity.class)
        );


    }

    @Test
    void register_userAlreadyExists() {

        when(
            userRepository
            .existsByEmail(registerRequestDto.getEmail())
        )
        .thenReturn(true);


        assertThrows(
            PublicBadRequestException.class,
            () -> authService.register(registerRequestDto)
        );


        verify(
            userRepository,
            never()
        )
        .saveAndFlush(
            any(UserEntity.class)
        );



    }

    @Test
    void login_success() {

        when(
            authenticationManager
            .authenticate(any(UsernamePasswordAuthenticationToken.class)) // Mock successful authentication
        )
        .thenReturn(null);

        when(
            userRepository
            .findByEmail(loginRequestDto.getEmail())
        )
        .thenReturn(
            Optional.of(testUser)
        );

        when(
            jwtService
            .generateToken(any(UserEntity.class))
        )
        .thenReturn("jwtToken");



        AccessTokenResponseDto response = authService
                                                .login(loginRequestDto);



        assertNotNull(response);

        assertEquals(
            "jwtToken",
            response.getAccessToken()
        );

        verify(
            userRepository
        )
        .findByEmail(
            loginRequestDto.getEmail()
        );



    }

    @Test
    void login_invalidCredentials() {

        when(
            authenticationManager
            .authenticate(any(UsernamePasswordAuthenticationToken.class))
        )
        .thenThrow(
            new BadCredentialsException("Invalid credentials")
        );


        assertThrows(
            PublicBadRequestException.class,
            () -> authService.login(loginRequestDto)
        );


        verify(
            authenticationManager
        )
        .authenticate(
            any(UsernamePasswordAuthenticationToken.class)
        );



    }

    @Test
    void login_userNotFound() {

        when(
            authenticationManager
            .authenticate(any(UsernamePasswordAuthenticationToken.class)) // Mock success autentication
        )
        .thenReturn(null);

        when(
            userRepository
            .findByEmail(loginRequestDto.getEmail())
        )
        .thenReturn(Optional.empty());


        assertThrows(
            PublicNotFoundException.class,
            () -> authService.login(loginRequestDto)
        );


        verify(
            userRepository
        )
        .findByEmail(loginRequestDto.getEmail());



    }



}




