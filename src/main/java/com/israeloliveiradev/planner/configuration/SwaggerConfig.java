package com.israeloliveiradev.planner.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI PlannerOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Planner API")
                        .description("Planner API")
                        .version("v1.0.0")
                        .license(new License()
                        .name("Israel Oliveira")
                        .url("https://www.linkedin.com/in/israeloliveiradev/"))
                        .contact(new Contact()
                                .name("Israel Oliveira")
                                .url("https://github.com/israeloliveiradev")
                                .email("israeloliveiracontact@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/israeloliveiradev"));

    }
    @Bean
    OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
                    .forEach(operation -> {

                        ApiResponses apiResponses = operation.getResponses();

                        apiResponses.addApiResponse("200", createApiResponse("Requisição realizada com sucesso"));
                        apiResponses.addApiResponse("201", createApiResponse("Objeto persistido com sucesso"));
                        apiResponses.addApiResponse("204", createApiResponse("Objeto excluído com sucesso"));
                        apiResponses.addApiResponse("400", createApiResponse("Erro na requisição do cliente"));
                        apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado"));
                        apiResponses.addApiResponse("403", createApiResponse("Acesso proibido"));
                        apiResponses.addApiResponse("404", createApiResponse("Não encontrado"));
                        apiResponses.addApiResponse("500", createApiResponse("Erro interno no servidor"));
                    }));
        };
    }

    private ApiResponse createApiResponse(String description) {
        return new ApiResponse().description(description);
    }
}


