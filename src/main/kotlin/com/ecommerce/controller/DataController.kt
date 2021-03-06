package com.ecommerce.controller

import com.ecommerce.constant.APIMessage
import com.ecommerce.service.DataService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.apache.commons.io.FilenameUtils
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
            ResponseEntity<Any>(APIMessage.EMPTY_DB, HttpStatus.INTERNAL_SERVER_ERROR)
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
            ResponseEntity<Any>(APIMessage.EMPTY_DB, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return response
    }

    @ApiOperation(value = "function to return dataset, with optional pagination")
    @RequestMapping(value = ["/search/wildcard"], method = [(RequestMethod.GET)])
    fun getByWildcard(
            @ApiParam(value = "text to search for ", required = true)
            @RequestParam(value = "text", required = true)
            text: String,

            @ApiParam(value = "page number to retrieve", required = false, defaultValue = "1")
            @RequestParam(value = "page", required = false, defaultValue = "1")
            page: Int,

            @ApiParam(value = "size of each page", required = false, defaultValue = "10")
            @RequestParam(value = "size", required = false, defaultValue = "10")
            size: Int)

            : ResponseEntity<Any> {

        var response: ResponseEntity<Any>

        response = if (dataService.hasData()) {
            var resultList = dataService.getDataByWildcardField(text, page, size)
            ResponseEntity(resultList, HttpStatus.OK)
        } else {
            ResponseEntity<Any>(APIMessage.EMPTY_DB, HttpStatus.INTERNAL_SERVER_ERROR)
        }
        return response
    }

    @ApiOperation(value = "function to accept csv file for querying")
    @RequestMapping(value = ["/upload"], method = [(RequestMethod.POST)])
    fun handleFileUpload(
            @ApiParam(value = "file to save. Only CSV files are acceptable", required = true)
            @RequestParam("file") file: MultipartFile)
            : ResponseEntity<Any> {

        var responseEntity =  ResponseEntity<Any>(APIMessage.FILL_UPLOAD_FAILURE, HttpStatus.INTERNAL_SERVER_ERROR)

        try {
            if (dataService.isCsv(file)) {
                dataService.save(file)
                responseEntity  = ResponseEntity<Any>(APIMessage.FILL_UPLOAD_SUCCESS, HttpStatus.OK)
            }
        } catch (e: Exception) {
            logger.error(e.message)
        }

        return responseEntity
    }
}