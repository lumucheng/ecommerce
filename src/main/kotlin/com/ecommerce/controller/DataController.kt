package com.ecommerce.controller

import com.ecommerce.service.DataService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

private const val EMPTY_DB_MESSAGE = "Empty database. Ensure CSV file has been uploaded before querying."

@RestController
@RequestMapping("/api")
class DataController {

    val logger: Logger = LoggerFactory.getLogger(DataController::class.java)

    @Autowired
    lateinit var dataService: DataService;

    @ApiOperation(value = "function to return dataset, with optional pagination")
    @RequestMapping(value = ["/search/all"], method = [(RequestMethod.GET)])
    fun getAll(
            @ApiParam(value = "page number to retrieve", required = false, defaultValue = "1")
            @RequestParam(value = "page", required = false, defaultValue = "1")
            page: Int,

            @ApiParam(value = "size of each page", required = false, defaultValue = "10")
            @RequestParam(value = "size", required = false, defaultValue = "10")
            size: Int)

            : ResponseEntity<Any> {

        var response: ResponseEntity<Any>

        response = if (dataService.hasData()) {
            var resultList = dataService.getData(page, size)
            ResponseEntity(resultList, HttpStatus.OK)
        } else {
            ResponseEntity<Any>(EMPTY_DB_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return response
    }

    @ApiOperation(value = "function to search data by country, with optional pagination")
    @RequestMapping(value = ["/search/country"], method = [(RequestMethod.GET)])
    fun getByCountry(
            @ApiParam(value = "country name to search by ", required = true)
            @RequestParam(value = "country", required = true)
            country: String,

            @ApiParam(value = "page number to retrieve", required = false, defaultValue = "1")
            @RequestParam(value = "page", required = false, defaultValue = "1")
            page: Int,

            @ApiParam(value = "size of each page", required = false, defaultValue = "10")
            @RequestParam(value = "size", required = false, defaultValue = "10")
            size: Int)

            : ResponseEntity<Any> {

        var response: ResponseEntity<Any>

        response = if (dataService.hasData()) {
            var resultList = dataService.getDataByCountry(country, page, size)
            ResponseEntity(resultList, HttpStatus.OK)
        } else {
            ResponseEntity<Any>(EMPTY_DB_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return response
    }

    @ApiOperation(value = "function to accept csv file for querying")
    @RequestMapping(value = ["/upload"], method = [(RequestMethod.POST)])
    fun handleFileUpload(
            @ApiParam(value = "file to save. Only CSV files are acceptable", required = true)
            @RequestParam("file") file: MultipartFile)
            : ResponseEntity<Any> {

        return try {
            dataService.save(file)
            ResponseEntity<Any>("CSV file uploaded successfully.", HttpStatus.OK)
        } catch (e: Exception) {
            logger.error(e.message)
            ResponseEntity<Any>("Something went wrong while processing. Please ensure the CSV file is valid.", HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}