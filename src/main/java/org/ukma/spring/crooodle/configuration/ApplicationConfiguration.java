package org.ukma.spring.crooodle.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("org.ukma.spring.crooodle.repository")
@Configuration
public class ApplicationConfiguration {

}

