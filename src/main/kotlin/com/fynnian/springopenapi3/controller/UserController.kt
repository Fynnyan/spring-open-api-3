package com.fynnian.springopenapi3.controller

import com.fynnian.springopenapi3.domain.*
import com.fynnian.springopenapi3.repositories.GeneralRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
class UserController(private val generalRepository: GeneralRepository) {

  @GetMapping
  @Operation(summary = "Get all users")
  fun getUsers(): List<User> = generalRepository.getUsers()


  @GetMapping("/{userId}")
  @Operation(summary = "Get a user by id")
  fun getUser(@PathVariable userId: String): User =
      generalRepository.getUserById(UserId(userId)) ?: throw EntryNotFound("There is no user with userId: $userId")

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Create a new user")
  fun createUser(@RequestBody @Valid user: UserCreateRequest) {
    generalRepository.createUser(user.toDomain())
  }

  @PutMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Update a user", description = "Update a part of the user, requires that at least one property of the user request is present in the request body")
  fun updateUser(@PathVariable userId: String,
                 @RequestBody @Valid user: UserUpdateRequest) {
    generalRepository.updateUser(user.patchUser(
        generalRepository.getUserById(UserId(userId)) ?: throw EntryNotFound("There is no user with userId: $userId")))
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Delete a user")
  fun deleteUser(@PathVariable userId: String) {
    generalRepository.deleteUser(UserId(userId))
  }
}
