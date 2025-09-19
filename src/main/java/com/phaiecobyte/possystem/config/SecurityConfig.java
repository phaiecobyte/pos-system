package com.phaiecobyte.possystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "POS System API", description = "Point of Sale System Service.", version = "v1"))
@SecurityScheme(
        name = "keycloak",
        scheme = "bearer",
        type = SecuritySchemeType.OPENIDCONNECT,
        in = SecuritySchemeIn.HEADER,
        openIdConnectUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}/.well-known/openid-configuration"
)
public class SecurityConfig {

}