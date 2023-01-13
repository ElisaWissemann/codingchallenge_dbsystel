package com.example.codingchallenge_dbsystel_backend.contoller

data class DistanceResponse(val from: String, val to: String, val distance: Int, val unit: String = "km")
