package com.example.kmm.course

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: String,
    val name: String,
    val courseDescription: String,
    val level: String,
    val specification: String
)



