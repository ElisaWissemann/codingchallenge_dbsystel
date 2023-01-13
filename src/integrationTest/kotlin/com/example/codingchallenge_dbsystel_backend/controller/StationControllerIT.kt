package com.example.codingchallenge_dbsystel_backend.controller

import com.example.codingchallenge_dbsystel_backend.contoller.DistanceResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StationControllerIT {

    @LocalServerPort
    private lateinit var port: String

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Test
    fun testStationsController() {
        val result = testRestTemplate
            .getForEntity("http://localhost:$port/api/v1/distance/FF/BL", DistanceResponse::class.java )

        val body = result.body!!
        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(body.from, "Frankfurt(Main)Hbf")
        assertEquals(body.to, "Berlin Hbf (tief)")
        assertEquals(body.distance, 423)
        assertEquals(body.unit, "km")
    }

    @Test
    fun returnsNotFoundWhenWrongStationIsProvided() {
        val result = testRestTemplate
            .getForEntity("/api/v1/distance/FF/not-existing", String::class.java )

        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.NOT_FOUND)
    }
}
