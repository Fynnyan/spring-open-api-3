package com.fynnian.springopenapi3.domain

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

open class ApiException(val apiError: ApiError): Exception(apiError.message)

class EntryNotFound(override val message: String): ApiException(ApiError(message, HttpStatus.NOT_FOUND))

data class ApiError(val message: String,
                    val status: HttpStatus,
                    val timestamp: LocalDateTime = LocalDateTime.now())