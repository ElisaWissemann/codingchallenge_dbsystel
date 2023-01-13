package com.example.codingchallenge_dbsystel_backend.contoller


import com.example.codingchallenge_dbsystel_backend.service.StationsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/** This StationsController is responsible to handle all REST-Request*/

// Controllers responsibilities: listening to the HTTP request, validating the input,
// calling the business logic, serializing the output, and translating the Exceptions to a proper response
@RequestMapping("api/v1")
@RestController
class StationsController(val service: StationsService) {

    /**Function that handels the GET-Request*/
    //ds100 is the abbreviation for station/destination representation in the CSV file
    @GetMapping("/distance/{ds100First}/{ds100Second}")
    fun getDistance(
        @PathVariable ds100First: String,
        @PathVariable ds100Second: String
    ) = service.getDistanceBetween(ds100First,ds100Second)
}


