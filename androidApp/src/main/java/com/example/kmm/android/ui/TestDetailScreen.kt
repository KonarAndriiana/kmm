package com.example.kmm.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmm.android.test.TestDetailViewModel

@Composable
fun TestDetailScreen(
    testId: String,
    vm: TestDetailViewModel = viewModel()
) {
    val detail by vm.detail.collectAsState()
    LaunchedEffect(testId) { vm.fetchDetail(testId) }

    detail?.let { d ->
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(d.title, style = MaterialTheme.typography.headlineLarge)
            Text(d.description, style = MaterialTheme.typography.bodyLarge)

            d.questions.forEachIndexed { i, q ->
                Card(
                    Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Q${i + 1}: ${q.question}", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        q.options.forEach { opt ->
                            Text("• $opt", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Loading…", style = MaterialTheme.typography.bodyLarge)
    }
}