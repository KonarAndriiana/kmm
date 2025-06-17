package com.example.kmm.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kmm.tests.QuestionDto
import com.example.kmm.android.test.TestDetailViewModel

@Composable
fun TestDetailScreen(
    testId: String,
    vm: TestDetailViewModel = viewModel()
) {
    val detail by vm.detail.collectAsState()
    LaunchedEffect(testId) {
        vm.fetchDetail(testId)
    }

    detail?.let { d ->
        // track selected option index per question
        val selections: SnapshotStateMap<Int, Int> = remember { mutableStateMapOf() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = d.title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = d.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            d.questions.forEachIndexed { qIndex, q ->
                QuestionCard(
                    question = q,
                    questionIndex = qIndex,
                    selections = selections
                )
            }
        }
    } ?: Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading…",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun QuestionCard(
    question: QuestionDto,
    questionIndex: Int,
    selections: SnapshotStateMap<Int, Int>
) {
    // current selected option for this question
    val selectedIdx by remember { derivedStateOf { selections[questionIndex] } }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Q${(questionIndex + 1).toString().padStart(2, '0')}: ${question.question}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            question.options.forEachIndexed { idx, option ->
                val isSelected = (selectedIdx == idx)
                val isCorrect = option == question.answer
                val background = when {
                    isSelected && isCorrect -> Color(0xFFA8E6CF)
                    isSelected && !isCorrect -> Color(0xFFFF8C94)
                    else -> Color.Transparent
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background, RoundedCornerShape(8.dp))
                        .clickable {
                            // record selection; later taps override
                            selections[questionIndex] = idx
                        }
                        .padding(12.dp)
                ) {
                    Text(
                        text = "${'A' + idx}.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = option,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
