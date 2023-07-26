package com.irdaislakhuafa.garbagepickupapi.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class RestAPIDocsConfiguration {
    @Bean
    public OpenAPI openAPI() {
        final var result = new OpenAPI() {{
            setInfo(new Info() {{
                title("Garbage Pickup Rest API Documentation");
                description("The Documentation of Garbage Pickup Application's REST API provides a comprehensive guide for developers and users on how to communicate with the API of the Garbage Pickup application. This official documentation is designed to facilitate a better understanding and seamless integration of the services offered by the Garbage Pickup application.");
                version("1.0");
            }});
        }};

        return result;
    }
}