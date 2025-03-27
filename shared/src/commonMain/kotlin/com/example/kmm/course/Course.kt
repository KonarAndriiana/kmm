package com.example.kmm.course

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)



