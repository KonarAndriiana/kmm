package com.example.kmm.android.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm.course.Course
import com.example.kmm.course.CourseApi
import com.example.kmm.course.HttpClientProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CourseViewModel : ViewModel() {
    private val api = CourseApi(HttpClientProvider().getClient())

    private val _courseList = MutableStateFlow<List<Course>>(emptyList())
    val courseList: StateFlow<List<Course>> = _courseList

    fun fetchCourses() {
        viewModelScope.launch {
            try {
            _courseList.value = api.getCourses()
        } catch (e: Exception) {
                _courseList.value = emptyList()
                println("Error fetching courses: ${e.message}")
            }
        }
    }
}