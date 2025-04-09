package com.example.kmm.course

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CourseApi(private val client: HttpClient) {
    suspend fun getCourses(): List<Course> {
        return client.get("https://jsonplaceholder.typicode.com/posts").body()
    }

    suspend fun getCourseById(id: Int): Course? {
        return getCourses().find { it.id == id }
    }
}