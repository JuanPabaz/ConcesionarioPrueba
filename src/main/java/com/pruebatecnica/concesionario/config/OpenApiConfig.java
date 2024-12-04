package com.pruebatecnica.concesionario.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Juan Pablo",
                        email = "jpgthf@gmail.com",
                        url = "https://github.com/JuanPabaz/ConcesionarioPrueba"
                ),
                description = "Documentacion de OpenApi para prueba tecnica Benfit con Spring Security",
                title = "Especificacion OpenApi - Juan Pablo",
                version = "1.0"
        ),
        servers = @Server(
                description = "Ambiente Local",
                url = "http://localhost:8080/api/1.0"
        ),
        security = {
                @SecurityRequirement(
                        name = "BearerToken"
                )
        }
)
@SecurityScheme(
        name = "BearerToken",
        description = "Autenticacion JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
