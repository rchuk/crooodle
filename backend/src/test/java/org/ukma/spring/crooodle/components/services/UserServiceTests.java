package org.ukma.spring.crooodle.components.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.ukma.spring.crooodle.entities.UserEntity;
import org.ukma.spring.crooodle.entities.UserPermissionEntity;
import org.ukma.spring.crooodle.entities.enums.UserPermission;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.repository.UserPermissionRepository;
import org.ukma.spring.crooodle.repository.UserRepository;
import org.ukma.spring.crooodle.service.impl.UserServiceImpl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * Component (unit) tests for the {@link UserServiceImpl} class
 *
 * testGetCurrentUser_Success:
 * Tests the successful retrieval of the current user based on the authentication context.
 *
 * testGetCurrentUser_NotFound:
 * Error scenario - the requested user is not found in the database, resulting in a NoSuchElementException.
 *
 * testLoadUserByUsername_Success:
 * Tests the successful loading of a user by username (email) when the user exists in the database.
 *
 * testLoadUserByUsername_NotFound:
 * Error scenario - the requested user is not found by username (email), resulting in a UsernameNotFoundException.
 *
 * testAddPermission_Success:
 * Tests the successful addition of a permission to a user, when the user and permission exist.
 *
 * testAddPermission_UserNotFound:
 * Error scenario - the user is not found by email, resulting in a PublicNotFoundException.
 *
 * testAddPermission_PermissionNotFound:
 * Error scenario - the permission is not found, resulting in a NoSuchElementException.
 */

class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPermissionRepository permissionRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity testUser;
    private UserPermission testPermission;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        testUser = UserEntity
            .builder()
            .id(1L)
            .name("testUser")
            .email("test@example.com")
            .passwordHash("somePasswordHash")
            .build();

        testPermission = UserPermission.USER_ADMIN;



    }

    @Test
    void testGetCurrentUser_Success() {

        Authentication authentication = mock(Authentication.class);

        when(
            authentication
            .getName()
        )
        .thenReturn(testUser.getEmail());


        SecurityContextHolder
            .getContext()
            .setAuthentication(authentication);


        when(
            userRepository
            .findByEmail(testUser.getEmail())
        )
        .thenReturn(
            Optional.of(testUser)
        );


        UserEntity currentUser = userService
                                    .getCurrentUser();


        assertNotNull(currentUser);

        assertEquals(
            "test@example.com",
            currentUser.getEmail()
        );

        verify(
            userRepository
        )
        .findByEmail(
            testUser.getEmail()
        );
    }

    @Test
    void testGetCurrentUser_NotFound() {

        var email = "test@example.com";

        when(
            userRepository.findByEmail(email)
        )
        .thenReturn(Optional.empty());


        assertThrows(
            NoSuchElementException.class,
            () -> userService.getCurrentUser()
        );


        verify(
            userRepository
        )
        .findByEmail(email);



    }

    @Test
    void testLoadUserByUsername_Success() {

        when(
            userRepository
            .findByEmail("test@example.com")
        )
        .thenReturn(
            Optional.of(testUser)
        );


        var userDetails = userService
                                .loadUserByUsername("test@example.com");


        assertNotNull(userDetails);

        assertEquals(
            "test@example.com",
            userDetails.getUsername()
        );

        verify(
            userRepository
        )
        .findByEmail("test@example.com");



    }

    @Test
    void testLoadUserByUsername_NotFound() {

        when(
            userRepository
                .findByEmail("test@example.com")
        )
        .thenReturn(Optional.empty());


        assertThrows(
            UsernameNotFoundException.class,
            () -> userService.loadUserByUsername("test@example.com")
        );

        verify(
            userRepository
        )
        .findByEmail("test@example.com");



    }

    @Test
    void testAddPermission_Success() {

        UserPermissionEntity permissionEntity = UserPermissionEntity // mock UserPermissionEntity
            .builder()
            .kind(testPermission)
            .build();


        testUser                                // init permissions
            .setPermissions(new HashSet<>());

        when(
            userRepository
            .findByEmail("test@example.com")
        )
        .thenReturn(
            Optional.of(testUser)
        );

        when(
            permissionRepository
            .findByKind(testPermission)
        )
        .thenReturn(
            Optional.of(permissionEntity)
        );



        userService
            .addPermission(
                "test@example.com",
                testPermission
            );


        assertTrue(
            testUser.getPermissions()
                .contains(permissionEntity)
        );

        verify(
            userRepository
        )
        .saveAndFlush(
            testUser
        );



    }



    @Test
    void testAddPermission_UserNotFound() {

        when(
            userRepository
            .findByEmail("test@example.com")
        )
        .thenReturn(Optional.empty());


        assertThrows(
            PublicNotFoundException.class,
            () -> userService.addPermission("test@example.com", testPermission)
        );


        verify(
            userRepository
        )
        .findByEmail("test@example.com");

        verify(
            permissionRepository,
            never()
        )
        .findByKind(testPermission);

        verify(
            userRepository,
            never()
        )
        .saveAndFlush(any());


    }

    @Test
    void testAddPermission_PermissionNotFound() {

        when(
            userRepository
            .findByEmail("test@example.com")
        )
        .thenReturn(
            Optional.of(testUser)
        );

        when(
            permissionRepository
            .findByKind(testPermission)
        )
        .thenReturn(Optional.empty());


        assertThrows(
            NoSuchElementException.class,
            () -> userService.addPermission("test@example.com", testPermission)
        );


        verify(
            userRepository
        )
        .findByEmail("test@example.com");

        verify(
            permissionRepository
        )
        .findByKind(testPermission);

        verify(
            userRepository,
            never()
        )
        .saveAndFlush(any());



    }



}



