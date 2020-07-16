package com.fynnian.springopenapi3.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {

  @Bean
  fun openApiConfig(): OpenAPI {
    return OpenAPI()
        .info(Info().title("Test API")
                    .description("Demo to showcase the springdoc usage")
                    .version("1")
                    .license(License().name("MIT")))
  }
}