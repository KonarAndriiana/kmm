package com.example.kmm.android.course

import androidx.lifecycle.ViewModel
import com.example.kmm.course.Course
import com.example.kmm.course.CourseApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CourseViewModel : ViewModel() {
    private val apiService = CourseApiService()

    private val _course = MutableStateFlow<Course?>(null)
    val course: StateFlow<Course?> = _course

    fun fetchCourse(courseId: Int) {
        _course.value = null
        _course.value = apiService.getCourse(courseId)
    }
}