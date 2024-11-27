package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.RegisterTypeDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.entities.enums.UserPermission;
import org.ukma.spring.crooodle.exception.PublicBadRequestException;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.entities.UserEntity;
import org.ukma.spring.crooodle.repository.UserRepository;
import org.ukma.spring.crooodle.service.AuthService;
import org.ukma.spring.crooodle.service.JwtService;
import org.ukma.spring.crooodle.service.UserService;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    private final static Set<UserPermission> TRAVELER_PERMISSIONS = Set.of(

    );
    private final static Set<UserPermission> HOTEL_OWNER_PERMISSIONS = Set.of(
        UserPermission.HOTEL_REQUEST_CREATION,
        UserPermission.HOTEL_EDIT,
        UserPermission.HOTEL_VIEW,
        UserPermission.HOTEL_DELETE,

        UserPermission.ROOM_CREATE,
        UserPermission.ROOM_EDIT,
        UserPermission.ROOM_VIEW,
        UserPermission.ROOM_DELETE
    );

    @Transactional
    @Override
    public AccessTokenResponseDto register(UserRegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail()))
            throw new PublicBadRequestException("User with given email is already registered");

        var user = UserEntity.builder()
            .name(registerRequestDto.getName())
            .email(registerRequestDto.getEmail())
            .passwordHash(passwordEncoder.encode(registerRequestDto.getPassword()))
            .permissions(new HashSet<>())
            .build();

        userRepository.saveAndFlush(user);

        userService.addPermissions(user.getEmail(), getPermissions(registerRequestDto.getRegisterType()));

        return AccessTokenResponseDto.builder()
            .accessToken(jwtService.generateToken(user))
            .build();
    }

    @Transactional
    @Override
    public AccessTokenResponseDto login(UserLoginRequestDto loginRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new PublicBadRequestException("Invalid username or password");
        }

        var user = userRepository.findByEmail(loginRequestDto.getEmail())
            .orElseThrow(() -> new PublicNotFoundException("User with given email doesn't exist"));

        return AccessTokenResponseDto.builder()
            .accessToken(jwtService.generateToken(user))
            .build();
    }

    public Set<UserPermission> getPermissions(RegisterTypeDto registerTypeDto) {
        return switch (registerTypeDto) {
            case TRAVELER -> TRAVELER_PERMISSIONS;
            case HOTEL_OWNER -> HOTEL_OWNER_PERMISSIONS;
        };
    }
}
