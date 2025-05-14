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
        val allLectures: List<Lecture> = client
            .get("https://raw.githubusercontent.com/KonarAndriiana/kmm/refs/heads/main/lectures_full.json")
            .body()
        return allLectures.find { it.id == id }
    }
}

//suspend fun getLectureById(id: String): Lecture? {
//    val passwordsLectures: Lecture = client
//        .get("https://raw.githubusercontent.com/KonarAndriiana/kmm/refs/heads/main/lectures_full.json")
//        .body()
//    return if (passwordsLectures.id == id) passwordLectures else null
//}
//}