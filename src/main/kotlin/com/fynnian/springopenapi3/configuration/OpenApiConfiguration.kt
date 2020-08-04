package com.fynnian.springopenapi3.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration @Autowired constructor(private val buildProperties: BuildProperties) {

  @Bean
  fun openApiConfig(): OpenAPI {
    return OpenAPI()
        .info(Info().title("Test API")
                    .description("Demo to showcase the springdoc usage")
                    .version(buildProperties.version)
                    .license(License().name("MIT")))
  }
}