package com.example.kmm.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.kmm.android.auth.AuthViewModel
import com.example.kmm.android.auth.AuthViewModelFactory
import com.example.kmm.android.course.CourseViewModel

@Composable
fun CourseScreen(navController: NavController, courseId: Int? = null) {
    val courseViewModel: CourseViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .padding(16.dp)
    ) {

        // Logout button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = {
                authViewModel.logout()
                navController.navigate("login") {
                    popUpTo("courseList") { inclusive = true }
                }
            }) {
                Text("Logout")
            }
        }

        if (courseId == null) {
            val courses by courseViewModel.courseList.collectAsState()
            LaunchedEffect(Unit) { courseViewModel.fetchCourses() }

            Text(text = "Available Courses", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))

            courses.forEach { course ->
                Text(
                    text = course.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { navController.navigate("course/${course.id}") },
                    style = MaterialTheme.typography.bodyLarge
                )
                Divider()
            }
        } else {
            val course by courseViewModel.course.collectAsState()
            LaunchedEffect(courseId) { courseViewModel.fetchCourse(courseId) }

            course?.let {
                Text(text = "Course: ${it.title}", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Description:\n${it.description}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.navigate("courseList") }) {
                    Text("Back to Courses")
                }
            } ?: Text(text = "Loading...")
        }
    }
}