package com.example.codingchallenge_dbsystel_backend.service

import com.example.codingchallenge_dbsystel_backend.contoller.DistanceResponse
import com.example.codingchallenge_dbsystel_backend.exceptions.StationNotFoundException
import com.example.codingchallenge_dbsystel_backend.repository.StationsRepository
import org.springframework.stereotype.Service
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * This StationsService calculates the distance between two stations and builds up the DistanceResponse */
@Service
class StationsService(val repository: StationsRepository) {

    /**
     * Calculate distance between two given destinations and builds DistanceResponse
     * */
    //ds100 is the abbreviation for station/destination representation in the CSV file
    fun getDistanceBetween(ds100First: String, ds100Second: String): DistanceResponse? {
        val data = repository.findStations(ds100First, ds100Second)
        if (data.size != 2) {
            throw StationNotFoundException("No station found for input parameters $ds100First and $ds100Second")
        }
        //website to test the distance manually https://www.omnicalculator.com/other/latitude-longitude-distance
        val distance = calculateDistance(
            data[0].lat,
            data[0].lon,
            data[1].lat,
            data[1].lon
        )
        return DistanceResponse(data[0].name, data[1].name, distance.toInt())
    }

    /**
     * Function to calculate distance between two destinations */
    private fun calculateDistance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {

        val R = 6371 // earth radius // km
        val phi1 = lat1 * Math.PI / 180
        val phi2 = lat2 * Math.PI / 180
        val dLat = (lat2 - lat1) * Math.PI / 180
        val dLon = (long2 - long1) * Math.PI / 180

        val a = sin(dLat / 2) * sin(dLat / 2) +
                sin(dLon / 2) * sin(dLon / 2) * cos(phi1) * cos(phi2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return R * c
    }
}