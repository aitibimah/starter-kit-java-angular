package com.yourcompany.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public OpenAPI projectApi() {
        // strip trailing slash if present
        String base = issuerUri.endsWith("/")
            ? issuerUri.substring(0, issuerUri.length() - 1)
            : issuerUri;

        // derive the OIDC endpoints
        String authUrl  = base + "/protocol/openid-connect/auth";
        String tokenUrl = base + "/protocol/openid-connect/token";

        // 1) Bearer JWT scheme
        SecurityScheme bearer = new SecurityScheme()
            .name("bearerAuth")
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .description("Paste your JWT here");

        // 2) OAuth2 Authorization Code flow
        OAuthFlow codeFlow = new OAuthFlow()
            .authorizationUrl(authUrl)
            .tokenUrl(tokenUrl)
            .scopes(new Scopes()
                .addString("openid",  "OpenID Connect scope")
                .addString("profile", "User profile info")
                .addString("email",   "User email")
                .addString("roles",   "User roles")
            );

        SecurityScheme oauth2 = new SecurityScheme()
            .name("oauth2")
            .type(SecurityScheme.Type.OAUTH2)
            .description("Authorization Code flow with Keycloak")
            .flows(new OAuthFlows().authorizationCode(codeFlow));

        return new OpenAPI()
            .info(new Info()
                .title("Project API")
                .description("Secure endpoints with Keycloak")
                .version("1.0")
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://www.apache.org/licenses/LICENSE-2.0")))
            .addSecurityItem(new SecurityRequirement()
                .addList("bearerAuth")
                .addList("oauth2"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", bearer)
                .addSecuritySchemes("oauth2",     oauth2));
    }
}
