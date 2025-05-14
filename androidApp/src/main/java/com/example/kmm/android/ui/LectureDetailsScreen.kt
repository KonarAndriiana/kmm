package com.example.kmm.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmm.android.lecture.LectureViewModel

@Composable
fun LectureDetailsScreen(
    lectureId: String,
    lectureViewModel: LectureViewModel = viewModel()
) {
    val lecture by lectureViewModel.lecture.collectAsState()

    LaunchedEffect(lectureId) {
        lectureViewModel.fetchLecture(lectureId)
    }

    lecture?.let { lec ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            lec.content.forEach { block ->
                when (block.type) {
                    "title"    -> Text(block.content, style = MaterialTheme.typography.headlineMedium)
                    "subtitle" -> Text(block.content, style = MaterialTheme.typography.titleMedium)
                    "text"     -> Text(block.content, style = MaterialTheme.typography.bodyLarge)
                    else       -> { /* ignore */ }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    } ?: Text(
        "Loading…",
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}