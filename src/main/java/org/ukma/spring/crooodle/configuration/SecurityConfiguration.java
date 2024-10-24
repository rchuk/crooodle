package org.ukma.spring.crooodle.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.ukma.spring.crooodle.app.JwtAuthenticationFilter;
import org.ukma.spring.crooodle.service.UserService;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${app.pbkdf2.secret}")
    private String pbkdf2Secret;

    @Value("${app.pbkdf2.iterations}")
    private int pbkdf2Iterations;

    @Value("${app.pbkdf2.salt.length}")
    private int pbkdf2SaltLength;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(auth -> auth
                        // Дозволити доступ до сторінок логіну, реєстрації та помилок без аутентифікації
                        .requestMatchers("/auth/**", "/login", "/register", "/error").permitAll()
                        // Дозволити доступ до статичних ресурсів
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // Заборонити доступ до інших URL без аутентифікації
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login") // Налаштування сторінки логіну
                        .defaultSuccessUrl("/", true) // Перенаправлення на головну після успішного логіну
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для виходу
                        .logoutSuccessUrl("/auth/login") // Повернення на логін після виходу
                        .permitAll()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS)) // Безсесійний режим
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new Pbkdf2PasswordEncoder(
                pbkdf2Secret,
                pbkdf2SaltLength,
                pbkdf2Iterations,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        );
    }
}
