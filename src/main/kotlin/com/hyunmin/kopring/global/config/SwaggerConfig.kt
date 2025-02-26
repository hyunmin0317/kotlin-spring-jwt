package com.hyunmin.kopring.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .info(apiInfo())
        .components(Components().addSecuritySchemes("access-token", securityScheme()))
        .addSecurityItem(SecurityRequirement().addList("access-token"))

    private fun apiInfo(): Info = Info()
        .title("Application API Document")
        .description("This is Application's API document.")
        .version("v0.0.1")

    private fun securityScheme(): SecurityScheme = SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
}
