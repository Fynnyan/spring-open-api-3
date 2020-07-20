package com.fynnian.springopenapi3.repositories

import com.fynnian.springopenapi3.domain.EntryNotFound
import com.fynnian.springopenapi3.domain.User
import com.fynnian.springopenapi3.domain.UserId
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

  fun getUserById(userId: UserId): User? =
      jooq.selectFrom(USERS)
          .where(USERS.USER_ID.eq(userId))
          .fetchInto(USERS)
          .map { it.toDomain() }
          .firstOrNull()

  fun createUser(user: User) =
      jooq.executeInsert(user.toReccord())

  fun updateUser(user: User) =
      jooq.executeUpdate(user.toReccord())

  fun deleteUser(userId: UserId) =
      jooq.deleteFrom(USERS)
          .where(USERS.USER_ID.eq(userId))
          .execute().also { if (it == 0) throw EntryNotFound("There is no user with userId: ${userId.value}")  }

  private fun User.toReccord(): UsersRecord =
      UsersRecord(userId, firstName, lastName, language, role, active)

  private fun UsersRecord.toDomain(): User =
      User(userId = userId, firstName = firstName, lastName = lastName, language = language, role = role, active = active)
}
