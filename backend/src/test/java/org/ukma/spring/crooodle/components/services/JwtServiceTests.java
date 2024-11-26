package org.ukma.spring.crooodle.components.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.impl.JwtServiceImpl;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;


/**
 *
 * Component (unit) tests for the {@link JwtServiceImpl} class
 *
 * generateToken_success:
 * Tests the successful generation of a JWT token
 *
 * extractUserName_success:
 * Tests the successful extraction of the username from a valid token
 *
 * isTokenValid_validToken:
 * Tests that a token is valid when the username matches and the token is not expired
 *
 * isTokenValid_invalidToken_usernameMismatch:
 * Error scenario - token is invalid because used by wrong user
 *
 * isTokenValid_invalidToken_expired:
 * Error scenario - token is invalid because it expired
 *
 */


class JwtServiceTests {

    @Mock
    private JwtServiceImpl jwtService;


    private String jwtSecret = "ABPDtwb29pENZA1c+/lcpGXU1BSvL447WqvoxsTO4Y8=";  // sample secret key for testing, NOT USE IN PROD


    private int jwtExpirationSeconds = 3600;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {


        // Code for mock JWT regeneration

        /*SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);

        // Encode the key in Base64 to use as the JWT secret
        jwtSecret = Base64.getEncoder().encodeToString(keyBytes);

        // Print the generated key
        System.out.println("Generated JWT Secret: " + jwtSecret);*/


        MockitoAnnotations.openMocks(this);

        userDetails = User.builder()
            .username("testUser")
            .password("password")
            .roles("USER")
            .build();

        jwtService = JwtServiceImpl
            .builder()
            .jwtExpirationSeconds(jwtExpirationSeconds)
            .jwtSecret(jwtSecret)
            .build();



    }

    @Test
    void generateToken_success() {

        String token = jwtService
            .generateToken(userDetails);


        assertNotNull(token, "Token should not be null");

        assertTrue(
            token.startsWith("eyJ"),
            "Token should start with JWT prefix 'eyJ'"
        );



    }

    @Test
    void extractUserName_success() {

        String token = jwtService
            .generateToken(userDetails);


        String extractedUsername = jwtService
            .extractUserName(token);


        assertEquals(
            userDetails.getUsername(),
            extractedUsername,
                "Username extracted from token should match the input username"
        );



    }

    @Test
    void isTokenValid_validToken() {

        String token = jwtService
            .generateToken(userDetails);


        boolean isValid = jwtService
            .isTokenValid(token, userDetails);


        assertTrue(
            isValid,
            "Token should be valid when the username matches and token is not expired"
        );



    }

    @Test
    void isTokenValid_invalidToken_usernameMismatch() {


        UserDetails otherUser = User
            .builder()
            .username("differentUser")
            .password("password")
            .roles("USER")
            .build();


        String token = jwtService
            .generateToken(userDetails);



        boolean isValid = jwtService
            .isTokenValid(token, otherUser);



        assertFalse(
            isValid,
            "Token should be invalid when the username does not match"
        );



    }

    @Test
    void isTokenValid_invalidToken_expired() {

        String username = "testUser";

        long expirationTime = System.currentTimeMillis() - 1000L * jwtExpirationSeconds;  // past tense expiry time


        String token = Jwts
            .builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis() - 1000L * 60))  // issued 1 minute ago
            .setExpiration(new Date(expirationTime))  // expires immediately
            .compact();


        boolean isValid = jwtService
            .isTokenValid(token, userDetails);


        assertFalse(
            isValid,
            "Token should be invalid if it is expired"
        );



    }





}








