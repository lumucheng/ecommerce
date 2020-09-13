package com.ecommerce.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "COMMERCE")
data class Data (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false, updatable = false)
        private val id: Long = 0,

        @Column(name = "invoice_no", nullable = false)
        private val invoiceNo: String,

        @Column(name = "stock_code", nullable = false)
        val stockCode: String,

        @Column(name = "description", nullable = false)
        val description: String,

        @Column(name = "quantity", nullable = false)
        val quantity: Int,

        @Column(name = "invoice_date", nullable = true)
        val invoiceDate: LocalDateTime?,

        @Column(name = "unit_price", nullable = false)
        val unitPrice: BigDecimal,

        @Column(name = "customer_id", nullable = true)
        val customerId: Long?,

        @Column(name = "country", nullable = false)
        val country: String
)