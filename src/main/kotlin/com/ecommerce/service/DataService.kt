package com.ecommerce.service

import com.ecommerce.helper.CSVHelper
import com.ecommerce.model.Data
import com.ecommerce.repository.DataRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class DataService(private val dataRepository: DataRepository) {

    private val logger: Logger = LoggerFactory.getLogger(DataService::class.java)

    fun save(file: MultipartFile) {
        try {
            val dataList: List<Data> = CSVHelper.ingestCsvToDataModel(file)
            dataRepository.saveAll(dataList)
        } catch (e: IOException) {
            logger.error(e.message)
            throw e
        }
    }

    fun getData(page : Int, size: Int): List<Data> {
        val paging: Pageable = PageRequest.of(page, size)
        val dataPages = dataRepository.findAll(paging)
        return dataPages.content
    }

    fun getDataByCountry(country:String, page : Int, size: Int): MutableList<Data?> {
        val paging: Pageable = PageRequest.of(page, size)
        val dataPages = dataRepository.findByCountry(country, paging)
        return dataPages?.content ?: mutableListOf<Data?>()
    }
}