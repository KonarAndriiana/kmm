package com.example.kmm.android.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm.tests.TestSummary
import com.example.kmm.tests.TestApi
import com.example.kmm.course.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestListViewModel : ViewModel() {
    private val api = TestApi(HttpClientProvider().getClient())

    private val _tests = MutableStateFlow<List<TestSummary>>(emptyList())
    val tests: StateFlow<List<TestSummary>> = _tests

    fun fetchTests() = viewModelScope.launch {
        runCatching { api.getTests() }
            .onSuccess { _tests.value = it }
            .onFailure { _tests.value = emptyList() }
    }
}