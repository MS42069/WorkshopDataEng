package com.fhwedel.softwareprojekt.v1.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import java.time.LocalTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =
                @Info(
                        title = "OpenAPI definition - Softwareprojekt WS22 - Stundenplan",
                        description =
                                """
						Documentation of the Stundenplan Backend-Server API based on the OpenAPI-Specification Version 3.0.
						This documentation was generated using [springdoc-openapi](https://springdoc.org/).
						"""))
@Configuration
public class OpenAPIConfiguration {

    /**
     * Registers a bean definition to customize the global OpenAPI object
     *
     * @return a configured instance of {@link OpenAPI}
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // Replace the incorrectly generated LocalTime schema with a custom string schema
        return new OpenAPI()
                .components(
                        new Components().addSchemas("LocalTime", createCustomLocalTimeSchema()));
    }

    /**
     * Creates a custom schema to represent a time as text string.
     *
     * @return string schema for {@link LocalTime}
     */
    private Schema<String> createCustomLocalTimeSchema() {
        Schema<String> schema = new StringSchema();
        schema.setDescription(
                "Represents a time using the format 'hh:mm:ss' (the seconds are optional)");
        return schema;
    }
}
