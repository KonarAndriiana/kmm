package com.example.kmm.lecture

import kotlinx.serialization.Serializable

@Serializable
data class Lecture(
    val id: String,
    val courseId: String,
    val name: String,
    val info: String,
    val content: List<LectureContent>
)

@Serializable
data class LectureContent(
    val type: String,
    val content: String
)
