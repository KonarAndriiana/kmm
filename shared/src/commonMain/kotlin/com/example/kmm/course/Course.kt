package com.example.kmm.course

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: String,
    val title: String,
    val description: String,
    val lectures: List<Lecture>
)



