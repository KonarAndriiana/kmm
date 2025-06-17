package com.example.kmm.android.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm.tests.TestDetail
import com.example.kmm.tests.TestApi
import com.example.kmm.course.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestDetailViewModel : ViewModel() {
    private val api = TestApi(HttpClientProvider().getClient())

    private val _detail = MutableStateFlow<TestDetail?>(null)
    val detail: StateFlow<TestDetail?> = _detail

    fun fetchDetail(id: String) = viewModelScope.launch {
        runCatching { api.getTestDetail(id) }
            .onSuccess { _detail.value = it }
            .onFailure { _detail.value = null }
    }
}