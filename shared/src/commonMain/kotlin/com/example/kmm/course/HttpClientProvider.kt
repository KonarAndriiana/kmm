package com.example.kmm.course

import io.ktor.client.HttpClient

expect class HttpClientProvider {
    fun getClient(): HttpClient
}