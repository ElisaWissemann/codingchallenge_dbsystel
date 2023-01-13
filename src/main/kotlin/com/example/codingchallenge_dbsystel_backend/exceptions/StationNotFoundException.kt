package com.example.codingchallenge_dbsystel_backend.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class StationNotFoundException(message: String) : RuntimeException(message)
