package com.fynnian.springopenapi3.repositories

import com.fynnian.springopenapi3.domain.*
import com.fynnian.springopenapi3.jooq.springdoc.Tables.INVOICES
import com.fynnian.springopenapi3.jooq.springdoc.Tables.USERS
import com.fynnian.springopenapi3.jooq.springdoc.tables.records.InvoicesRecord
import com.fynnian.springopenapi3.jooq.springdoc.tables.records.UsersRecord
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class GeneralRepository(private val jooq: DSLContext) {

  // Users

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
      jooq.executeInsert(user.toRecord())

  fun updateUser(user: User) =
      jooq.executeUpdate(user.toRecord())

  fun deleteUser(userId: UserId) =
      jooq.deleteFrom(USERS)
          .where(USERS.USER_ID.eq(userId))
          .execute().also { if (it == 0) throw EntryNotFound("There is no user with userId: ${userId.value}")  }

  private fun User.toRecord(): UsersRecord =
      UsersRecord(userId, firstName, lastName, language, role, active)

  private fun UsersRecord.toDomain(): User =
      User(userId = userId, firstName = firstName, lastName = lastName, language = language, role = role, active = active)

  // Invoices

  fun createInvoice(invoice: Invoice) =
      jooq.executeInsert(invoice.toRecord())

  fun getInvoices(): List<Invoice> =
      jooq.selectFrom(INVOICES)
          .fetchInto(INVOICES)
          .map { it.toDomain() }

  private fun Invoice.toRecord(): InvoicesRecord =
      when (this) {
          is StandardInvoice -> InvoicesRecord(invoiceId, category, userId, title, price, payed, dueDate, creationDate, null)
          is SubscriptionInvoice -> InvoicesRecord(invoiceId, category, userId, title, price, payed, dueDate, creationDate, month)
          else -> throw Exception("$this is not implemented ")
      }


  private fun InvoicesRecord.toDomain(): Invoice =
      when (category) {
          InvoiceCategory.STANDARD -> StandardInvoice(
              invoiceId = invoiceId,
              userId = userId,
              title = title,
              price = price,
              payed = payed,
              dueDate = duedate,
              creationDate = creationdate)

          InvoiceCategory.SUBSCRIPTION -> SubscriptionInvoice(
              invoiceId = invoiceId,
              userId = userId,
              title = title,
              price = price,
              payed = payed,
              dueDate = duedate,
              creationDate = creationdate,
              month = month)
      }
}
