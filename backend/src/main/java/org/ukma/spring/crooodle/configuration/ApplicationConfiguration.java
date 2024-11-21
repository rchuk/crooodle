package org.ukma.spring.crooodle.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories("org.ukma.spring.crooodle.repository")
@Configuration
public class ApplicationConfiguration {
    @Bean
    public OpenAPI openaPi() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
            .components(
                new Components()
                    .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                    )
            )
            .security(List.of(new SecurityRequirement().addList(securitySchemeName)))
            .info(new Info().title("Crooodle").version("0.1.0"));
    }
}

