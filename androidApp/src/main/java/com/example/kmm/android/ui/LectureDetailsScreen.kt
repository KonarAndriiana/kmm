package com.example.kmm.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top-level title LECTURE
            Text(
                text = lec.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
            )

            // each block in a little Card
            lec.content.forEach { block ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        when (block.type) {
                            "title" -> Text(
                                text = block.content,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            "subtitle" -> Text(
                                text = block.content,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            "text" -> {
                                // support simple bullet lists
                                block.content.lines().forEach { line ->
                                    if (line.trimStart().startsWith("-")) {
                                        Row {
                                            Text("• ", style = MaterialTheme.typography.bodyLarge)
                                            Text(
                                                text = line.trimStart().removePrefix("-").trim(),
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }
                                    } else {
                                        Text(
                                            text = line,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                            else -> { /* ignore unknown types */ }
                        }
                    }
                }
            }
        }
    } ?: Text(
        "Loading…",
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}