package org.ukma.spring.crooodle.components.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.ukma.spring.crooodle.controller.AuthController;
import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.repository.UserPermissionRepository;
import org.ukma.spring.crooodle.service.AuthService;
import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.UserService;
import org.ukma.spring.crooodle.service.impl.UserPermissionSeederImpl;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // because I use @BeforeAll
@ComponentScan(basePackages = "org.ukma.spring.crooodle")
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserPermissionRepository userPermissionRepository;

    @MockBean
    private UserPermissionSeederImpl userPermissionSeeder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthService authService;



    // ALLEGEDLY CORRECT VALUES
    private UserRegisterRequestDto validRegisterRequest;
    private UserLoginRequestDto validLoginRequest;
    private AccessTokenResponseDto validAccessTokenResponse;


    // INVALIDS
    private UserRegisterRequestDto invalidEmail_RegisterRequestDto;
    private UserRegisterRequestDto invalidName_RegisterRequestDto;
    private UserRegisterRequestDto invalidPass_RegisterRequestDto;

    private UserLoginRequestDto invalidPass_LoginRequestDto;
    private UserLoginRequestDto invalidEmail_LoginRequestDto;


    @BeforeAll
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        // Correct input data

        validRegisterRequest = UserRegisterRequestDto
            .builder()
            .name("username")
            .email("mockemail@gmail.com")
            .password("passwordas")
            .build();

        validLoginRequest = UserLoginRequestDto
            .builder()
            .email("mockemail@gmail.com")
            .password("passwordas")
            .build();

        validAccessTokenResponse = AccessTokenResponseDto
            .builder()
            .accessToken("sampletoken") //TODO change to real one
            .build();


        // Incorrect input data

        invalidEmail_RegisterRequestDto = UserRegisterRequestDto
            .builder()
            .name("username")
            .email("invalidEmail") // Invalid email format
            .password("passwordas")
            .build();

        invalidName_RegisterRequestDto = UserRegisterRequestDto
            .builder()
            .name("") // Invalid name (empty)
            .email("mockemail@gmail.com")
            .password("passwordas")
            .build();

        invalidPass_RegisterRequestDto = UserRegisterRequestDto.builder()
            .name("username")
            .email("mockemail@gmail.com")
            .password("short") // invalid password (short)
            .build();



        invalidPass_LoginRequestDto = UserLoginRequestDto.builder()
            .email("mockemail@gmail.com")
            .password("short") // invalid password (short)
            .build();

        invalidEmail_LoginRequestDto = UserLoginRequestDto.builder()
            .email("abuboba123") // invalid email
            .password("passwordas")
            .build();

    }

    // TESTS FOR ENSURING CORRECT BEHAVIOUR

    @Test
    @WithAnonymousUser
    void register_withValidRequest_returnsAccessToken() throws Exception {

        when(
            authService
            .register(validRegisterRequest)
        )
            .thenReturn(validAccessTokenResponse);


        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRegisterRequest)))
                .andExpect(status().isOk())
                .andExpect(
                    jsonPath("$.accessToken")
                    .value("mockAccessToken")
                );


        verify(authService, times(1))
            .register(validRegisterRequest);
    }

    @Test
    @WithAnonymousUser
    void login_withValidRequest_returnsAccessToken() throws Exception {

        when(
            authService
            .login(validLoginRequest)
        )
            .thenReturn(validAccessTokenResponse);


        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(
                    jsonPath("$.accessToken")
                    .value("mockAccessToken")
                );


        verify(authService, times(1))
            .login(validLoginRequest);
    }

    // TESTS FOR HANDLING BAD BEHAVIOUR

    @Test
    @WithAnonymousUser
    void register_withInvalidEmail_returnsBadRequest() throws Exception {

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmail_RegisterRequestDto)))
                .andExpect(
                    status()
                    .isBadRequest()
                );


        verify(authService, never())
            .register(any());
    }

    @Test
    @WithAnonymousUser
    void register_withInvalidName_returnsBadRequest() throws Exception {

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidName_RegisterRequestDto)))
                .andExpect(
                    status()
                    .isBadRequest()
                );


        verify(authService, never())
            .register(any());
    }

    @Test
    @WithAnonymousUser
    void register_withInvalidPassword_returnsBadRequest() throws Exception {

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidPass_RegisterRequestDto)))
                .andExpect(
                    status()
                    .isBadRequest()
                );


        verify(authService, never())
            .register(any());
    }

    @Test
    @WithAnonymousUser
    void login_withInvalidPassword_returnsBadRequest() throws Exception {

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidPass_LoginRequestDto)))
                .andExpect(
                    status()
                    .isBadRequest()
                );


        verify(authService, never())
            .login(any());
    }

    @Test
    @WithAnonymousUser
    void login_withInvalidEmail_returnsBadRequest() throws Exception {

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmail_LoginRequestDto)))
                .andExpect(
                    status()
                    .isBadRequest()
                );


        verify(authService, never())
            .login(any());
    }

}

