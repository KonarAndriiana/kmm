package com.example.kmm.course

import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponse(
    val courses: List<Course>
)