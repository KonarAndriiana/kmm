package com.example.kmm.lecture

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class LectureApi(private val client: HttpClient) {

    suspend fun getLectures(): List<LectureSummary> {
        val response: LecturesResponse = client
            .get("https://raw.githubusercontent.com/KonarAndriiana/kmm/refs/heads/main/lectures.json")
            .body()
        return response.lectures
    }

    suspend fun getLectureById(id: String): Lecture? {
        // list of all LectureDetails endpoints
        val urls = listOf(
            "https://raw.githubusercontent.com/KonarAndriiana/kmm/main/lectures_full.json",
            "https://raw.githubusercontent.com/KonarAndriiana/kmm/main/lectures_full2.json",
            "https://raw.githubusercontent.com/KonarAndriiana/kmm/main/lectures_full3.json"
        )
        // try each one in turn
        for (url in urls) {
            runCatching {
                client.get(url).body<Lecture>()
            }.onSuccess { lecture ->
                if (lecture.id == id) return lecture
            }
            // onFailure is ignored so we keep trying the next URL
        }
        return null
    }
}