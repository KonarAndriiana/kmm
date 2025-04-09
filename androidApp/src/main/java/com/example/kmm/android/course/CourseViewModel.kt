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
    private val api = CourseApi(HttpClientProvider().createClient())

    private val _course = MutableStateFlow<Course?>(null)
    val course: StateFlow<Course?> = _course

    private val _courseList = MutableStateFlow<List<Course>>(emptyList())
    val courseList: StateFlow<List<Course>> = _courseList

    fun fetchCourse(courseId: Int) {
        viewModelScope.launch {
            _course.value = null
            _course.value = api.getCourseById(courseId)
        }
    }

    fun fetchCourses() {
        viewModelScope.launch {
            _courseList.value = api.getCourses()
        }
    }
}