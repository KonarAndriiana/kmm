package com.example.kmm.lecture

import kotlinx.serialization.Serializable

@Serializable
data class Lecture(
    val id: String,
    val name: String,
    val description: String,
    val level: String,
    val specification: String
)
