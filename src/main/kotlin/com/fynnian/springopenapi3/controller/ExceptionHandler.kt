package com.fynnian.springopenapi3.controller

import com.fynnian.springopenapi3.domain.ApiError
import com.fynnian.springopenapi3.domain.ApiException
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

  @ExceptionHandler(ApiException::class)
  @ApiResponses(
      value = [
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ApiError::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ApiError::class))])
      ])
  fun handleAPiExceptions(exception: ApiException): ApiError {
    log.error("The following error occurred: ${exception.apiError}")
    return exception.apiError
  }

  companion object {
    val log: Logger = LoggerFactory.getLogger(this::class.java)
  }
}