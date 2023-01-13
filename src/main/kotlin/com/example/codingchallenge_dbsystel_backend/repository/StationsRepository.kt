package com.example.codingchallenge_dbsystel_backend.repository

import com.example.codingchallenge_dbsystel_backend.entities.Station
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Repository
import org.springframework.util.ResourceUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * This StationsRepository has access to the D_Bahnhof_2020_alle.csv file
 * This file is loaded once on startup*/
@Repository
class StationsRepository {

    /**Variable that holds the reference to the CSV file which is loaded only once*/
    private lateinit var stationCSV: List<Station>

    /**
     * Function that gives back the two rows with the given ds100 values
     **/
    //ds100 is the abbreviation for station/destination representation in the CSV file
    fun findStations(ds100First:String, ds100Second:String) =
        stationCSV.filter{ it.ds100 == ds100First || it.ds100 == ds100Second }

    /**
     * Function to load CSV only once when the SpringBoot App is started*/
    @PostConstruct
    private fun loadStations() {
        stationCSV = readAndParseStations()
    }

    /**
     * Function that reads the CSV file and parses it to a InputStreamObject*/
    private fun readAndParseStations(): List<Station> {
        //Get CSV File
        val file: File = ResourceUtils.getFile("classpath:D_Bahnhof_2020_alle.csv")
        // make inputStream
        val inputStream: InputStream = FileInputStream(file)
        //readCSV and get List<Bahnhof> returned
        return readCsv(inputStream)
    }

    /**
     * Function that transforms the CSV to a List that only contains the datafields that the StationService needs
     **/
    private fun readCsv(inputStream: InputStream): List<Station> {
        val reader = inputStream.bufferedReader()
        return reader.lineSequence()
            .filterIndexed { i, line -> i > 0 && line.isNotBlank() }
            .map {
                val splitList = it.split(';', ignoreCase = false)
                Station(
                    ds100 = splitList[1].trim(),
                    name = splitList[3].trim(),
                    lon = splitList[5].trim().replace(',', '.').toDouble(),
                    lat = splitList[6].trim().replace(',', '.').toDouble(),
                )
            }.toList()
    }
}
