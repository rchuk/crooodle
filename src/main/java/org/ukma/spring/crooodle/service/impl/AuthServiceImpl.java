package org.ukma.spring.crooodle.service.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    // Геттери для часу останнього виконання завдань (якщо потрібно відображати цю інформацію)
    @Getter
    private LocalDateTime lastCacheClearTime;
    @Getter
    private LocalDateTime lastUserActivityLogTime;

    @Override
    @CacheEvict(value = "authCache", key = "#registerRequestDto.email", beforeInvocation = true)
    public AccessTokenResponseDto register(UserRegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new PublicBadRequestException("User with given email is already registered");
        }

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
    @Cacheable(value = "authCache", key = "#loginRequestDto.email")
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

    // Фонове завдання для очищення кешу з крон-виразом
    @Scheduled(cron = "${cron.cache.clear.schedule:0 0 0 * * ?}") // Виконання кожен день о 00:00 (налаштовується через application.properties)
    @CacheEvict(value = "authCache", allEntries = true)
    public void clearCache() {
        try {
            logger.info("Початок очищення кешу...");
            lastCacheClearTime = LocalDateTime.now();
            logger.info("Кеш 'authCache' очищено о " + lastCacheClearTime);
        } catch (Exception e) {
            logger.error("Помилка при очищенні кешу: ", e);
        }
    }

    // Друге фонове завдання з фіксованою частотою
    @Scheduled(fixedRateString = "${fixedRate.user.activity.log:1800000}") // Запуск кожні 30 хвилин (налаштовується через application.properties)
    public void logUserActivity() {
        try {
            logger.info("Початок перевірки активності користувачів...");
            lastUserActivityLogTime = LocalDateTime.now();
            logger.info("Активність користувачів перевірено о " + lastUserActivityLogTime);
            // Додатковий код для логування або перевірки активності користувачів
        } catch (Exception e) {
            logger.error("Помилка при перевірці активності користувачів: ", e);
        }
    }
}
