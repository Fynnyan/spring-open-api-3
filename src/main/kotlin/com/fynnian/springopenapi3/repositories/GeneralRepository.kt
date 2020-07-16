package com.fynnian.springopenapi3.repositories

import com.fynnian.springopenapi3.domain.User
import com.fynnian.springopenapi3.jooq.springdoc.Tables.USERS
import com.fynnian.springopenapi3.jooq.springdoc.tables.records.UsersRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class GeneralRepository(private val jooq: DSLContext) {

  fun getUsers(): List<User> =
      jooq.selectFrom(USERS)
          .fetchInto(USERS)
          .map { it.toDomain() }

  fun getUserById(userId: String): User? =
      jooq.selectFrom(USERS)
          .where(USERS.USER_ID.eq(userId))
          .fetchInto(USERS)
          .map { it.toDomain() }
          .firstOrNull()

  fun createUser(user: User) =
      jooq.executeInsert(user.toReccord())

  fun updateUser(user: User) =
      jooq.executeUpdate(user.toReccord())

  fun deleteUser(userId: String) =
      jooq.deleteFrom(USERS)
          .where(USERS.USER_ID.eq(userId))
          .execute()

  private fun User.toReccord(): UsersRecord =
      UsersRecord(userId, firstName, lastName, language, role, active)

  private fun UsersRecord.toDomain(): User =
      User(userId = userId, firstName = firstName, lastName = lastName, language = language, role = role, active = active)
}