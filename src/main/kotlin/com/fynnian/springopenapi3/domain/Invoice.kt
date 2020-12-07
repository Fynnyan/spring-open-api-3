package com.fynnian.springopenapi3.domain

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import kotlin.random.Random

abstract class Invoice (
    open val invoiceId: InvoiceId,
    open val category: InvoiceCategory,
    open val userId: UserId,
    open val title: String,
    open val price: Double,
    open val payed: Boolean,
    open val dueDate: LocalDateTime,
    open val creationDate: LocalDateTime
)

data class StandardInvoice(
    override val invoiceId: InvoiceId = InvoiceId(),
    override val category: InvoiceCategory = InvoiceCategory.STANDARD,
    override val userId: UserId,
    override val title: String,
    override val price: Double,
    override val payed: Boolean = false,
    override val dueDate: LocalDateTime = LocalDateTime.now().plusDays(15),
    override val creationDate: LocalDateTime = LocalDateTime.now()
) : Invoice(invoiceId, category, userId, title, price, payed, dueDate, creationDate)

data class SubscriptionInvoice(
    override val invoiceId: InvoiceId = InvoiceId(),
    override val category: InvoiceCategory = InvoiceCategory.SUBSCRIPTION,
    override val userId: UserId,
    override val title: String,
    override val price: Double,
    override val payed: Boolean = false,
    override val dueDate: LocalDateTime = LocalDateTime.now().plusDays(30),
    override val creationDate: LocalDateTime = LocalDateTime.now(),
    val month: Int
): Invoice(invoiceId, category, userId, title, price, payed, dueDate, creationDate)

@Schema(enumAsRef = true)
enum class InvoiceCategory {
    STANDARD,
    SUBSCRIPTION
}

data class InvoiceCreationRequest(
    val category: InvoiceCategory = InvoiceCategory.STANDARD,
    val userId: String,
    val title: String,
    val price: Double) {

    fun toDomain() = when(category) {
        InvoiceCategory.STANDARD -> StandardInvoice(userId = UserId(userId), title = title, price = price)
        InvoiceCategory.SUBSCRIPTION -> SubscriptionInvoice(userId = UserId(userId), title = title, price = price, month = Random.nextInt())
    }
}
