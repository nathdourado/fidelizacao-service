package br.com.bakeflow.bakeflow.application;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("BakeFlow API")
                .description("BakeFlow API Documento")
                .contact(new Contact().name("BakeFlow API") )
                .version("1.0"));

    }
}