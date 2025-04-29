package com.example.kmm.lecture

import kotlinx.serialization.Serializable

@Serializable
data class LecturesResponse(
    val lectures: List<LectureSummary>
)

@Serializable
data class LectureSummary(
    val id: String,
    val courseId: String,
    val name: String,
    val info: String
)