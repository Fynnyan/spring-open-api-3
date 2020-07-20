package com.fynnian.springopenapi3.controller

import com.fynnian.springopenapi3.domain.*
import com.fynnian.springopenapi3.repositories.GeneralRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val generalRepository: GeneralRepository) {

  @GetMapping
  fun getUsers(): List<User> = generalRepository.getUsers()


  @GetMapping("/{userId}")
  fun getUser(@PathVariable userId: String): User =
      generalRepository.getUserById(UserId(userId)) ?: throw EntryNotFound("There is no user with userId: $userId")

  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun createUser(@RequestBody user: UserCreateRequest) {
    generalRepository.createUser(user.toDomain())
  }

  @PutMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun updateUser(@PathVariable userId: String,
                 @RequestBody user: UserUpdateRequest) {
    generalRepository.updateUser(user.patchUser(
        generalRepository.getUserById(UserId(userId)) ?: throw EntryNotFound("There is no user with userId: $userId")))
  }

  @DeleteMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteUser(@PathVariable userId: String) {
    generalRepository.deleteUser(UserId(userId))
  }
}
