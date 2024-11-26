package org.ukma.spring.crooodle.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(
    info = @Info(title = "Crooodle", version = "0.1.0"),
    security = @SecurityRequirement(name = "Bearer")
)
@EnableJpaRepositories("org.ukma.spring.crooodle.repository")
@Configuration
public class ApplicationConfiguration {

}

