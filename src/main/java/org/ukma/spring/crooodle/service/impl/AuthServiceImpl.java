package org.ukma.spring.crooodle.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ukma.spring.crooodle.dto.AccessTokenResponseDto;
import org.ukma.spring.crooodle.dto.UserLoginRequestDto;
import org.ukma.spring.crooodle.dto.UserRegisterRequestDto;
import org.ukma.spring.crooodle.exception.PublicBadRequestException;
import org.ukma.spring.crooodle.exception.PublicNotFoundException;
import org.ukma.spring.crooodle.model.User;
import org.ukma.spring.crooodle.model.enums.UserRoleKind;
import org.ukma.spring.crooodle.repository.UserRepository;
import org.ukma.spring.crooodle.repository.UserRoleRepository;
import org.ukma.spring.crooodle.service.AuthService;
import org.ukma.spring.crooodle.service.JwtService;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccessTokenResponseDto register(UserRegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail()))
            throw new PublicBadRequestException("User with given email is already registered");

        var userRole = userRoleRepository.findByName(UserRoleKind.USER)
                .orElseThrow(() -> new PublicNotFoundException("Role not found"));

        var user = User.builder()
                .name(registerRequestDto.getName())
                .email(registerRequestDto.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequestDto.getPassword()))
                .roles(Set.of(userRole))
                .build();

        userRepository.saveAndFlush(user);

        return AccessTokenResponseDto.builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }

    @Override
    public AccessTokenResponseDto login(UserLoginRequestDto loginRequestDto) {
        var user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new PublicNotFoundException("User with given email doesn't exist"));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPasswordHash()))
            throw new RuntimeException("Wrong password");

        return AccessTokenResponseDto.builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
