package com.example.kmm.course

import kotlinx.serialization.Serializable

@Serializable
data class CourseListResponse(
    val coursesList: List<Course>
)