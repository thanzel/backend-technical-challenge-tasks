package com.application.tasks.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Configuración de Swagger con la información pertinente a la APIRest
 * URL: http://localhost:8080/swagger-ui/index.html#/
 */
@OpenAPIDefinition(
        info = @Info (
                title = "APIRest Registro de Tareas",
                description = "Prueba Técnica para optar al cargo: Desarrollador BackEnd",
                version = "1.0.0",
                contact = @Contact (
                        name = "Yenny Chipamo",
                        url = "https://thanzel.github.io/portafolio-yenny/index.html",
                        email = "yenny.chipamo@gmail.com"
                )
        ),
        servers = {
                @Server (
                        description = "DESA_SERVER",
                        url = "http://localhost:8080"
                )
        }
)
public class SwaggerConfig {
}
