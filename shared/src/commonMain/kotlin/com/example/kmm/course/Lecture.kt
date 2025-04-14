package com.example.kmm.course

import kotlinx.serialization.Serializable

@Serializable
data class Lecture(
    val id: String,
    val title: String,
    val description: String,
    val requiredTime: String
)