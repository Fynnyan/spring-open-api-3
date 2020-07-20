package com.fynnian.springopenapi3.domain

import java.time.LocalDateTime

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

data class OneTimeInvoice(
    override val invoiceId: InvoiceId = InvoiceId(),
    override val category: InvoiceCategory = InvoiceCategory.ONE_TIME,
    override val userId: UserId,
    override val title: String,
    override val price: Double,
    override val payed: Boolean = false,
    override val dueDate: LocalDateTime = LocalDateTime.now().plusDays(30),
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
    override val creationDate: LocalDateTime = LocalDateTime.now()
): Invoice(invoiceId, category, userId, title, price, payed, dueDate, creationDate)


enum class InvoiceCategory {
    ONE_TIME,
    SUBSCRIPTION,
    LIFETIME,

}
