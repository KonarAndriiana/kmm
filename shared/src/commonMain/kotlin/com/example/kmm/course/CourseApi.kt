package com.example.kmm.course

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CourseApi(private val client: HttpClient) {
    suspend fun getCourses(): List<Course> {
        val response: CourseListResponse =
            client.get("https://raw.githubusercontent.com/PaloSatala/testJsons/refs/heads/main/coursesList.json").body()
        return response.coursesList
    }

    suspend fun getCourseById(id: Int): Course? {
        return getCourses().getOrNull(id)
    }
}