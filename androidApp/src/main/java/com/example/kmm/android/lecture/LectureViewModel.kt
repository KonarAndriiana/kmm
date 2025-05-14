package com.example.kmm.android.lecture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm.course.HttpClientProvider
import com.example.kmm.lecture.Lecture
import com.example.kmm.lecture.LectureApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LectureViewModel : ViewModel() {
    private val api = LectureApi(HttpClientProvider().getClient())

    // list
    private val _lectures = MutableStateFlow<List<com.example.kmm.lecture.LectureSummary>>(emptyList())
    val lectures: StateFlow<List<com.example.kmm.lecture.LectureSummary>> = _lectures

    // single lecture detail
    private val _lecture = MutableStateFlow<Lecture?>(null)
    val lecture: StateFlow<Lecture?> = _lecture

    fun fetchLectures() = viewModelScope.launch {
        runCatching { api.getLectures() }
            .onSuccess { _lectures.value = it }
            .onFailure { _lectures.value = emptyList() }
    }

    fun fetchLecture(id: String) = viewModelScope.launch {
        runCatching { api.getLectureById(id) }
            .onSuccess { _lecture.value = it }
            .onFailure { _lecture.value = null }
    }
}