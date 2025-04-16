package com.example.kmm.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmm.android.course.CourseViewModel

@Composable
fun CourseDetailsScreen(courseId: String) {
    val courseViewModel: CourseViewModel = viewModel()
    val courseState = courseViewModel.course.collectAsState()

    LaunchedEffect(courseId) {
        courseViewModel.fetchCourse(courseId)
    }

    val course = courseState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(16.dp)
    ) {
        course?.let { selectedCourse ->
            Text(
                text = selectedCourse.title,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = selectedCourse.description)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Lectures:", style = MaterialTheme.typography.headlineMedium)

            selectedCourse.lectures.forEach { lecture ->

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "• ${lecture.title} (${lecture.requiredTime} min)",
                    fontSize = 18.sp
                )
                Text(
                    text = lecture.description
                )
            }
        } ?: Text("Loading...")
    }
}