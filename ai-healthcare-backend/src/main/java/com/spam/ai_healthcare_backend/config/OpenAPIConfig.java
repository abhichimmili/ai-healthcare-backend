package com.spam.ai_healthcare_backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "CareAI Healthcare API", 
				version = "1.0", 
				description = "CareAI Healthcare Management System APIs"), 
		security = {
				@SecurityRequirement(
						name = "bearerAuth"
						) 
				}
		)
@SecurityScheme(
		name = "bearerAuth", 
		type = SecuritySchemeType.HTTP, 
		scheme = "bearer", 
		bearerFormat = "JWT"
		)
public class OpenAPIConfig {
}