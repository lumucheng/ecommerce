package com.ecommerce.controller

import com.ecommerce.constant.APIMessage
import com.ecommerce.model.Data
import com.ecommerce.service.DataService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.MockKAnnotations
import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import java.io.File
import java.io.FileInputStream
import java.math.BigDecimal

@ExtendWith(SpringExtension::class)
@WebMvcTest(DataController::class)
@AutoConfigureMockMvc(addFilters = false)
internal class DataControllerTest {

    @MockBean
    lateinit var dataService: DataService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test file upload failure`() {
        val file = File.createTempFile("sample", ".txt")
        file.deleteOnExit()
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/upload").file("file", file.readBytes()))
                .andExpect(status().is5xxServerError)
                .andExpect(content().string(APIMessage.FILL_UPLOAD_FAILURE))
                .andReturn()
    }

    @Test
    fun `test get all failure`() {
        val requestParams = LinkedMultiValueMap<String, String>()
        requestParams.add("page", "1")
        requestParams.add("size", "10")

        mockMvc.get("/api/search/all") {
            params = requestParams
        }.andExpect {
            status { is5xxServerError }
            content { string(APIMessage.EMPTY_DB) }
        }
    }

    @Test
    fun `test get all success`() {
        val data = listOf(Data(invoiceNo = "536365", stockCode = "85123A", description = "WHITE HANGING HEART T-LIGHT HOLDER",
                quantity = 6, invoiceDate = null, unitPrice = BigDecimal("2.55"), customerId = 17850, country = "United Kingdom"))

        val jsonObjectMapper = ObjectMapper()
        `when`(dataService.hasData()).thenReturn(true)
        `when`(dataService.getData(1, 10)).thenReturn(data)

        val requestParams = LinkedMultiValueMap<String, String>()
        requestParams.add("page", "1")
        requestParams.add("size", "10")

        mockMvc.get("/api/search/all") {
            params = requestParams
        }.andExpect {
            status { isOk }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(jsonObjectMapper.writeValueAsString(data)) }
        }
    }
}