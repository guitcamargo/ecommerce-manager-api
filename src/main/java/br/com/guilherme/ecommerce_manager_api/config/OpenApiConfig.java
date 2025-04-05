package br.com.guilherme.ecommerce_manager_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI publicApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de gerenciamento de e-commerce")
                        .version("1.0")
                        .description("Documentação da API"));
    }
}
