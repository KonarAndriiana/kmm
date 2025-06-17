package com.example.kmm.tests

import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    val question: String,
    val options: List<String>,
    val answer: String
)

@Serializable
data class TestDetail(
    val id: String,
    val title: String,
    val description: String,
    val questions: List<QuestionDto>
)

@Serializable
data class TestDetailsResponse(
    val testDetails: List<TestDetail>
)