package com.example.kmm.course

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CourseApi {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getCourses(): List<Course> {
        val response: HttpResponse = client.get("https://jsonplaceholder.typicode.com/posts")
        return response.body()
    }

    suspend fun getCourseById(id: Int): Course? {
        return getCourses().find { it.id == id }
    }
}