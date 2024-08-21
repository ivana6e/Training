package com.example.project2.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // http://localhost:8080/swagger-ui/index.html#/

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Project2 Spring Boot API Document")
                .version("(=\uD83D\uDF66 ﻌ \uD83D\uDF66=)")
                .description(":･ﾟ✧(=✪ ᆺ ✪=):･ﾟ✧");

        String securitySchemeName = "JWT Authentication";
        SecurityRequirement securityRequirement =
                new SecurityRequirement().addList(securitySchemeName);
        Components components = new Components()
                .addSecuritySchemes(securitySchemeName,
                        new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
