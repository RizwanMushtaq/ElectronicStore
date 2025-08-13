package com.rizwanmushtaq.ElectronicStore.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "Bearer Authentication";
    SecurityScheme bearerAuthScheme = new SecurityScheme()
        .name(securitySchemeName)
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT");
    return new OpenAPI()
        .info(new Info()
            .title("Electronic Store API")
            .version("1.0.0")
            .description("API documentation for the Electronic Store project.")
            .summary("This project is done to experiment with Spring Boot.")
            .contact(new Contact()
                .name("Rizwan Mushtaq")
                .email("rizwanmushtaq15@gmail.com")
                .url("https://github.com/RizwanMushtaq"))
            .license(new License().name("Apache 2.0").url("http://springdoc.org"))
        )
        .components(new Components().addSecuritySchemes(
            securitySchemeName,
            bearerAuthScheme
        ));
  }
}
