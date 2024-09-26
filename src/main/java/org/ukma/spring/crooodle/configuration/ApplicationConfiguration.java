package org.ukma.spring.crooodle.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@EnableJpaRepositories("org.ukma.spring.crooodle.repository")
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
}

