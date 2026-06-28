package com.kildare.ledgerflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ledgerFlowOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LedgerFlow API")
                        .description("API de transferências financeiras com controle de concorrência, " +
                                     "idempotência e auditoria. Desenvolvida com Spring Boot 3.5.15 + PostgreSQL.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Kildare Geraldo")
                                .url("https://github.com/kildaregeraldo")));
    }
}