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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kmm.android.lecture.LectureViewModel

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
        itemsIndexed(lecturesFiltered) { index, lec ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("lecture/${lec.id}") },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    // Lec num, zero-padded to two digits
                    Text(
                        text = "Lecture ${(index + 1).toString().padStart(2, '0')}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                    )

                    Spacer(Modifier.height(4.dp))

                    // Lec name
                    Text(
                        text = lec.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}