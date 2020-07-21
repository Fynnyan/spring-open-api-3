package com.fynnian.springopenapi3.domain

import com.fasterxml.jackson.annotation.JsonProperty
import com.fynnian.springopenapi3.validation.UserUpdateRequestValidation
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotNull

data class User(
    @Schema(description = "the user's id, UUID", example = "495af36b-ccc4-413b-8b75-eb70f61bdf5f")
    val userId: UserId = UserId(),
    @Schema(description = "First name of the user", example = "Kōta")
    val firstName: String,
    @Schema(description = "Last name of the user", example = "Hirano")
    val lastName: String,
    @Schema(description = "Language", example = "EN")
    val language: Language,
    @Schema(description = "The user's role", example = "STANDARD")
    val role: UserRole,
    @Schema(description = "Boolean to display if the user is active", example = "true")
    val active: Boolean
) {

  @JsonProperty("userId")
  fun getUserId() = userId.value
}

enum class UserRole {
  STANDARD,
  PREMIUM,
  EMPLOYEE
}

data class UserCreateRequest(
    @field:NotNull
    @Schema(description = "First name of the user", example = "Kōta", required = true)
    val firstName: String,
    @field:NotNull
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

@UserUpdateRequestValidation
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
