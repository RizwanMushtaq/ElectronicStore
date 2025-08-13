package com.rizwanmushtaq.ElectronicStore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Electronic Store API")
            .version("1.0.0")
            .description("API documentation for the Electronic Store project.")
            .summary("This project is done to experiment with Spring Boot.")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }
}
