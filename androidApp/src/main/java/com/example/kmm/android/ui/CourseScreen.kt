package com.example.kmm.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kmm.android.course.CourseViewModel

@Composable
fun CourseScreen(navController: NavController, courseId: Int? = null) {
    val courseViewModel: CourseViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        // IF courseId is NULL, SHOW -> course LIST
        if (courseId == null) {
            val courses = listOf("Course 1", "Course 2", "Course 3")

            Text(text = "Available Courses", style = MaterialTheme.typography.headlineLarge)

            Spacer(modifier = Modifier.height(16.dp))

            courses.forEachIndexed { index, courseName ->
                Text(
                    text = courseName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("course/${index + 1}") },
                    style = MaterialTheme.typography.bodyLarge
                )
                Divider()
            }
        } else {

            // ELSE courseId is NOT NULL(user clicked on a course, courseId changes from null),
            // SHOW -> course DETAILS
            val course by courseViewModel.course.collectAsState()
            LaunchedEffect(courseId) { courseViewModel.fetchCourse(courseId) }

            course?.let {
                Text(text = "Course: ${it.title}", style = MaterialTheme.typography.headlineLarge)
                Text(text = "Description: ${it.description}")
                Text(text = "Instructor: ${it.instructor.name}")
                Text(text = "Duration: ${it.duration}")
                Text(text = "Difficulty: ${it.difficulty}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { navController.navigate("courseList") }) {
                    Text("Back to Courses")
                }
            } ?: Text(text = "Loading...")
        }
    }
}