package org.ukma.spring.crooodle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestClient;

@EnableJpaRepositories("org.ukma.spring.crooodle.repository")
// Дозволяємо Spring автоматично створювати реалізації репозиторіїв для роботи з базою даних
@Configuration
// Позначаємо клас як конфігураційний, він буде обробляти залежності та налаштування
public class ApplicationConfiguration {
    @Bean
    public RestClient getRestClient() {
        return RestClient.create();
    }
}

