package com.example.kmm.tests

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class TestApi(
    private val client: HttpClient
) {
    suspend fun getTests(): List<TestSummary> {
        val resp: TestsResponse = client
            .get("https://raw.githubusercontent.com/KonarAndriiana/kmm/refs/heads/main/tests.json")
            .body()
        return resp.tests
    }

    suspend fun getTestDetail(id: String): TestDetail? {
        val resp: TestDetailsResponse = client
            .get("https://raw.githubusercontent.com/KonarAndriiana/kmm/refs/heads/main/testsDetails.json")
            .body()
        return resp.testDetails.singleOrNull { it.id == id }
    }
}