package com.fynnian.springopenapi3.validation

import com.fynnian.springopenapi3.domain.UserUpdateRequest
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [UserUpdateRequestValidator::class])
@Target(allowedTargets = [AnnotationTarget.CLASS])
@Retention(AnnotationRetention.RUNTIME)
annotation class UserUpdateRequestValidation(
    val message: String = "The user request body is invalid",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = [])

class UserUpdateRequestValidator : ConstraintValidator<UserUpdateRequestValidation, UserUpdateRequest> {
  override fun isValid(value: UserUpdateRequest?, context: ConstraintValidatorContext): Boolean {
    if (value == null) return false
    return value.let {
      !(it.firstName == null && it.lastName == null && it.language == null && it.role == null && it.active == null)
    }
  }
}