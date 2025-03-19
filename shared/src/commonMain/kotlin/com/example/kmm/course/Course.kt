package com.example.kmm.course

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val instructor: Instructor,
    val duration: String,
    val difficulty: String
)

@Serializable
data class Instructor(
    val id: Int,
    val name: String,
    val bio: String
)



