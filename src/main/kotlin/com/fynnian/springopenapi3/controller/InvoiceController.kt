package com.fynnian.springopenapi3.controller

import com.fynnian.springopenapi3.domain.*
import com.fynnian.springopenapi3.repositories.GeneralRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/invoices")
@Tag(name = "Invoices")
class InvoiceController(private val generalRepository: GeneralRepository) {

    @GetMapping
    @Operation(summary = "Get all invoices")
    @ApiResponse(responseCode = "200", content = [Content(array = ArraySchema(schema = Schema(anyOf = [StandardInvoice::class, SubscriptionInvoice::class])))])
    fun getInvoices(): List<Invoice> = generalRepository.getInvoices()

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Create a new invoice")
    fun createInvoice(@RequestBody @Valid invoice: InvoiceCreationRequest) {
        generalRepository.createInvoice(invoice = invoice.toDomain())
    }

}
