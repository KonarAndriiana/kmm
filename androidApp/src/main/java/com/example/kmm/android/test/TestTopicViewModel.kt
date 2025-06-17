package com.example.kmm.android.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm.tests.TestTopic
import com.example.kmm.tests.TestTopicApi
import com.example.kmm.course.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestTopicViewModel : ViewModel() {
    private val api = TestTopicApi(HttpClientProvider().getClient())

    private val _topics = MutableStateFlow<List<TestTopic>>(emptyList())
    val topics: StateFlow<List<TestTopic>> = _topics

    fun fetchTopics() = viewModelScope.launch {
        runCatching { api.getTestTopics() }
            .onSuccess { _topics.value = it }
            .onFailure { _topics.value = emptyList() }
    }
}