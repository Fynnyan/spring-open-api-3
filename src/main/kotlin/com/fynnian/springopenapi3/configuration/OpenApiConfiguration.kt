package com.fynnian.springopenapi3.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.headers.Header
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.HeaderParameter
import org.springdoc.core.customizers.OpenApiCustomiser
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
        .components(components)
  }

  /**
   * Configuration bean, adding a global header for ever path and every operation.
   * Can be used to do other nifty stuff on a global level without the need to annotate every class and request mapping
   */
  @Bean
  fun addGlobalHeader(): OpenApiCustomiser {
    return OpenApiCustomiser { openApi ->
      openApi.paths.values
          .flatMap { pathItem -> pathItem.readOperations() }
          .forEach { operation -> operation.addParametersItem(HeaderParameter().`$ref`(ApiRefs.X_SUPER_COOL_HEADER)) }
    }
  }

  /**
   * List of components that are globally available, can and need to be referenced the swagger way.
   * components > type > key
   * example: #/components/headers/x-super-cool-global-header
   * Use the reference string on the refs field of the various annotations.
   *
   * @see [ApiRefs]
   */
  val components: Components = Components()
      .addHeaders("x-super-cool-global-header",
                  Header().example("cool value")
                          .description("Global header that is required for every request (not needed for the requests of the demo to work)")
                          .required(false)
                          .schema(StringSchema()))
}

object ApiRefs {
  const val X_SUPER_COOL_HEADER = "#/components/headers/x-super-cool-global-header"
}
