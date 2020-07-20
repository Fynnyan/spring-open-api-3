package com.fynnian.springopenapi3.domain

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class User(
    val userId: UserId = UserId(),
    val firstName: String,
    val lastName: String,
    val language: Language,
    val role: UserRole,
    val active: Boolean
)

enum class UserRole {
  STANDARD,
  PREMIUM,
  EMPLOYEE
}

data class UserCreateRequest(
    @Schema(description = "First name of the user", example = "Kōta", required = true)
    val firstName: String,
    @Schema(description = "Last name of the user", example = "Hirano", required = true)
    val lastName: String,
    @Schema(description = "Optional, default DE")
    val language: Language = Language.DE,
    @Schema(description = "Optional, default STANDARD")
    val role: UserRole = UserRole.STANDARD,
    @Schema(description = "Optional, default true")
    val active: Boolean = true
) {
  fun toDomain(): User =
      User(firstName = firstName,
           lastName = lastName,
           language = language,
           role = role,
           active = active)
}

data class UserUpdateRequest(
    @Schema(description = "First name of the user", example = "Bernard")
    val firstName: String?,
    @Schema(description = "Last name of the user", example = "Cornwell")
    val lastName: String?,
    @Schema(description = "Language of the user", example = "EN")
    val language: Language?,
    @Schema(description = "User role", example = "PREMIUM")
    val role: UserRole?,
    @Schema(description = "is the user active", example = "false")
    val active: Boolean?
) {
  fun patchUser(user: User) =
      user.copy(firstName = firstName ?: user.firstName,
                lastName = lastName ?: user.lastName,
                language = language ?: user.language,
                role = role ?: user.role,
                active = active ?: user.active)
}
