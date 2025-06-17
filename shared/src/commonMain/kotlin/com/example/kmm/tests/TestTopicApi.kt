package com.example.kmm.tests

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class TestTopicApi(
    private val client: HttpClient
) {
    suspend fun getTestTopics(): List<TestTopic> {
        val resp: TestTopicsResponse = client
            .get("https://raw.githubusercontent.com/KonarAndriiana/kmm/refs/heads/Android-App/testTopics.json")
            .body()
        return resp.testTopics
    }
}