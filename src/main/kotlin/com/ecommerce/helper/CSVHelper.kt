package com.ecommerce.helper

import com.ecommerce.model.Data
import com.opencsv.CSVReaderHeaderAware
import org.springframework.web.multipart.MultipartFile
import java.io.InputStreamReader
import java.io.Reader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object CSVHelper {
    private const val INVOICE_NO = "InvoiceNo"
    private const val STOCK_CODE = "StockCode"
    private const val DESCRIPTION = "Description"
    private const val QUANTITY = "Quantity"
    private const val INVOICE_DATE = "InvoiceDate"
    private const val UNIT_PRICE = "UnitPrice"
    private const val CUSTOMER_ID = "CustomerID"
    private const val COUNTRY = "Country"

    fun ingestCsvToDataModel(file : MultipartFile): List<Data> {
        val reader: Reader = InputStreamReader(file.inputStream)
        val csvReader = CSVReaderHeaderAware(reader)

        val resultList = mutableListOf<Data>()
        var line = csvReader.readMap()
        while (line != null) {

            val invoiceNo = line[INVOICE_NO]!!
            val stockCode = line[STOCK_CODE]!!
            val description = line[DESCRIPTION]!!
            val quantity = line[QUANTITY]!!.toInt()

            val invoiceDateStr = line[INVOICE_DATE]
            val invoiceDate : LocalDateTime?
            invoiceDate = if (invoiceDateStr != null && invoiceDateStr.isNotEmpty()) {
                LocalDateTime.parse(line[INVOICE_DATE]!!, DateTimeFormatter.ofPattern("M/d/yyyy H:m"))
            } else {
                null
            }

            val unitPrice = line[UNIT_PRICE]!!.toBigDecimal()
            val customerId = line[CUSTOMER_ID]?.toLongOrNull()
            val country = line[COUNTRY]!!

            val data = Data(
                    invoiceNo = invoiceNo,
                    stockCode = stockCode,
                    description = description,
                    quantity = quantity,
                    invoiceDate = invoiceDate,
                    unitPrice = unitPrice,
                    customerId = customerId,
                    country = country
            );

            resultList.add(data)
            line = csvReader.readMap()
        }

        return resultList;
    }
}