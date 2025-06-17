package com.example.kmm.tests

import kotlinx.serialization.Serializable

@Serializable
data class TestSummary(
    val id: String,
    val testId: String,
    val name: String
)

@Serializable
data class TestsResponse(
    val tests: List<TestSummary>
)