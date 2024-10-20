package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.exception.PublicBadRequestException;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.repository.UserRepository;
import org.ukma.spring.crooodle.service.AuthService;
import org.ukma.spring.crooodle.service.JwtService;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AccessTokenResponseDto register(UserRegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail()))
            throw new PublicBadRequestException("User with given email is already registered"); // TODO: Move to strings

        var user = User.builder()
                .name(registerRequestDto.getName())
                .email(registerRequestDto.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequestDto.getPassword()))
                .build();

        userRepository.saveAndFlush(user);

        return AccessTokenResponseDto.builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }

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
}
