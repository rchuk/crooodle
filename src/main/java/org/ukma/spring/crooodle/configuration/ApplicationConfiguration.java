package org.ukma.spring.crooodle.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@EnableJpaRepositories("org.ukma.spring.crooodle.repository")
@Configuration
public class ApplicationConfiguration {
    @Value("${app.pbkdf2.secret}")
    private String pbkdf2Secret;
    @Value("${app.pbkdf2.iterations}")
    private int pbkdf2Iterations;
    @Value("${app.pbkdf2.salt.length}")
    private int pbkdf2SaltLength;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new Pbkdf2PasswordEncoder(pbkdf2Secret, pbkdf2SaltLength, pbkdf2Iterations, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    }
}
