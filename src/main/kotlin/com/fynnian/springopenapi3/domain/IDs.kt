package com.fynnian.springopenapi3.domain

import java.util.*

abstract class ID(open val value: String)

data class UserId(override val value: String = UUID.randomUUID().toString()) :ID(value)
data class InvoiceId(override val value: String = UUID.randomUUID().toString()) :ID(value)
