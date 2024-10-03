package org.ukma.spring.crooodle.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@EnableJpaRepositories("org.ukma.spring.crooodle.repository")
@EnableWebSecurity
// Дозволяємо Spring автоматично створювати реалізації репозиторіїв для роботи з базою даних
@Configuration
// Позначаємо клас як конфігураційний, він буде обробляти залежності та налаштування
public class ApplicationConfiguration {

    @Value("${app.pbkdf2.secret}")
    private String pbkdf2Secret; // Значення секрету для алгоритму PBKDF2 із конфігураційного файлу

    @Value("${app.pbkdf2.iterations}")
    private int pbkdf2Iterations; // Кількість ітерацій для PBKDF2

    @Value("${app.pbkdf2.salt.length}")
    private int pbkdf2SaltLength; // Довжина salt для алгоритму PBKDF2

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        // Створюємо та повертаємо об'єкт PasswordEncoder, використовуючи алгоритм PBKDF2 з налаштуваннями
        return new Pbkdf2PasswordEncoder(pbkdf2Secret, pbkdf2SaltLength, pbkdf2Iterations,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // TODO: Enable

        return http.build();
    }

    @Bean
    public RestClient getRestClient() {
        return RestClient.create();
    }
}

