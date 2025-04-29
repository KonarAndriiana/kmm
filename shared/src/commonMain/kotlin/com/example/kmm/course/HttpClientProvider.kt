package com.example.kmm.course

import io.ktor.client.*

expect class HttpClientProvider {
    fun getClient(): HttpClient
}