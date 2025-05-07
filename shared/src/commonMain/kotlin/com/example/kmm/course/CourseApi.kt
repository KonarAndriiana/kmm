package com.example.kmm.course

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CourseApi(private val client: HttpClient) {
    suspend fun getCourses(): List<Course> {
        return try {
            val response: CoursesResponse = client
                .get("https://raw.githubusercontent.com/KonarAndriiana/kmm/refs/heads/Android-App/courses.json")
                .body()
            response.courses
        } catch (e: Exception) {
            println("Error fetching courses: ${e.message}")
            emptyList()
        }
    }



    suspend fun getCourseById(id: String): Course? {
        return getCourses().find { it.id == id }
    }


}