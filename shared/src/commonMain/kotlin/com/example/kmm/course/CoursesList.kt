package com.example.kmm.course

import kotlinx.serialization.Serializable

@Serializable
data class CoursesList(
    val coursesList: List<Course>
)