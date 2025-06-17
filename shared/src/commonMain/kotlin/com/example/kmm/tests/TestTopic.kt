package com.example.kmm.tests

import kotlinx.serialization.Serializable

@Serializable
data class TestTopic(
    val id: String,
    val name: String,
    val testDescription: String,
    val level: String,
    val specification: String
)

@Serializable
data class TestTopicsResponse(
    val testTopics: List<TestTopic>
)