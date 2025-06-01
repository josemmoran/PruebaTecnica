package com.sv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;



@Configuration
public class SwaggerConfig{

	@Bean
	OpenAPI api() {
		return new OpenAPI().info(
				new Info()
				.title("ATM sersaprosa")
				.version("V1.0")
				.description("Uso de spring jwt con spring security")
				);
	}
	 
}
