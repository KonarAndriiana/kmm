package com.example.kmm.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kmm.android.lecture.LectureViewModel
import com.example.kmm.lecture.LectureSummary

@Composable
fun LectureListByCourse(
    courseId: String,
    navController: NavController,
    lectureViewModel: LectureViewModel = viewModel()
) {
    // Observe & fetch when courseId changes
    val all by lectureViewModel.lectures.collectAsState(initial = emptyList())
    LaunchedEffect(courseId) {
        lectureViewModel.fetchLectures()
    }

    // show only those matching course
    val lecturesFiltered = remember(all, courseId) {
        all.filter { it.courseId == courseId }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(lecturesFiltered) { lec ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("lecture/${lec.id}") },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = lec.name,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}