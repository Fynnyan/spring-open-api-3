package com.fynnian.springopenapi3.repositories

import com.fynnian.springopenapi3.domain.InvoiceId
import com.fynnian.springopenapi3.domain.UserId
import org.jooq.Converter

class UserIdConverter : Converter<String, UserId> {
    override fun from(databaseObject: String?): UserId? = databaseObject?.run { UserId(this) }
    override fun to(userId: UserId?): String? = userId?.value
    override fun fromType(): Class<String> = String::class.java
    override fun toType(): Class<UserId> = UserId::class.java
}

class InvoiceIdConverter : Converter<String, InvoiceId> {
    override fun from(databaseObject: String?): InvoiceId? = databaseObject?.run { InvoiceId(this) }
    override fun to(userId: InvoiceId?): String? = userId?.value
    override fun fromType(): Class<String> = String::class.java
    override fun toType(): Class<InvoiceId> = InvoiceId::class.java
}
