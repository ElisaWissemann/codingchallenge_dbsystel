package com.example.codingchallenge_dbsystel_backend.service

import com.example.codingchallenge_dbsystel_backend.contoller.DistanceResponse
import com.example.codingchallenge_dbsystel_backend.entities.Station
import com.example.codingchallenge_dbsystel_backend.exceptions.StationNotFoundException
import com.example.codingchallenge_dbsystel_backend.repository.StationsRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat


class StationServiceTest {
    private val repoMock: StationsRepository = mockk()
    private val stationsService = StationsService(repoMock)

    //8000105;FF;de:06412:10;Frankfurt(Main)Hbf;FV;8,663789;50,107145;DB Station und Service AG;1866;
    //8011160;BLS;de:11000:900003201;Berlin Hbf;FV;13,369545;52,525592;DB Station und Service AG;1071;
    @Test
    fun `test to getDistanceBetween FF (Frankfurt(Main)Hbf) and BLS (Berlin Hbf)`() {
        //assign
        val ds100First = "BLS"
        val ds100Second = "FF"
        //act
        every { repoMock.findStations(ds100First, ds100Second) } returns listOf(
            Station(
                ds100Second,
                "Frankfurt(Main)Hbf",
                8.663789,
                50.107145
            ), Station(ds100First, "Berlin Hbf", 13.369545, 52.525592)
        )
        //assert
        val result = stationsService.getDistanceBetween(ds100First, ds100Second)
        assertThat(result).isEqualTo(DistanceResponse("Frankfurt(Main)Hbf", "Berlin Hbf", 423, "km"))
    }

    //8001723;HEBA;;Einbeck Otto-Hahn-Straße;RV;9,89290953;51,8144784;Ilmebahn GmbH;;neu
    //8004371;KRO;;Nörvenich-Rommelsheim;nur DPN;6,547586;50,782539;Rurtalbahn GmbH;;neu
    @Test
    fun `test to getDistanceBetween HEBA (Einbeck Otto-Hahn-Straße) and KRO (Nörvenich-Rommelsheim)`() {
        //assign
        val ds100First = "HEBA"
        val ds100Second = "KRO"
        //act
        every { repoMock.findStations(ds100First, ds100Second) } returns listOf(
            Station(
                ds100First,
                "Einbeck Otto-Hahn-Straße",
                9.89290953,
                51.8144784
            ), Station(
                ds100Second,
                "Nörvenich-Rommelsheim",
                6.547586,
                50.782539
            )
        )
        //assert
        val result = stationsService.getDistanceBetween(ds100First, ds100Second)
        assertThat(result).isEqualTo(DistanceResponse("Einbeck Otto-Hahn-Straße", "Nörvenich-Rommelsheim", 259, "km"))
    }

    //8001723;HEBA;;Einbeck Otto-Hahn-Straße;RV;9,89290953;51,8144784;Ilmebahn GmbH;;neu
    @Test
    fun `test to getDistanceBetween HEBA (Einbeck Otto-Hahn-Straße) and incorrect input parameter AFGTHDJKVB`() {
        //assert
        assertThrows(StationNotFoundException::class.java) {
            //assign
            val ds100First = "HEBA"
            val ds100Second = "AFGTHDJKVB"
            //act
            every { repoMock.findStations(ds100First, ds100Second) } returns listOf(
                Station(
                    ds100First,
                    "Einbeck Otto-Hahn-Straße",
                    9.89290953,
                    51.8144784
                )
            )
            stationsService.getDistanceBetween(ds100First, ds100Second)
        }
    }
}

