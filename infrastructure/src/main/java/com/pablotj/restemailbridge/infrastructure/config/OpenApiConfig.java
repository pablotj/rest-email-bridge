package com.pablotj.restemailbridge.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Rest Email Bridge API")
                        .version("v1")
                        .description("API for sending and managing emails")
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }


    @Bean
    public OpenApiCustomizer globalHeaderCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation ->
                        operation.addParametersItem(
                                new Parameter()
                                        .in("header")
                                        .name("Accept-Language")
                                        .description("Language for messages (en, es, gl)")
                                        .required(false)
                                        .schema(new StringSchema()
                                                ._default("en")
                                                .addEnumItem("en")
                                                .addEnumItem("es")
                                                .addEnumItem("gl"))
                        )
                )
        );
    }
}