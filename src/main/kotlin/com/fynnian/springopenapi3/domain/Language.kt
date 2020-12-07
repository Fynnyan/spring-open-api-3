package com.fynnian.springopenapi3.domain

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus

@Schema(enumAsRef = true)
enum class Language {
    DE,
    FR,
    IT,
    EN;

    val code: String
        get() = this.name.toLowerCase()

    companion object {

        fun from(string: String) = when (string.toUpperCase()) {
            "DE" -> DE
            "FR" -> FR
            "IT" -> IT
            "EN" -> EN
            else -> when (string) {
                "german" -> DE
                "french" -> FR
                "italian" -> IT
                "english" -> EN
                else -> throw ApiException(ApiError(message = "There is no language option for: $string", status = HttpStatus.BAD_REQUEST))
            }
        }
    }
}
