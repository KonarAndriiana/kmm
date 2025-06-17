package com.example.kmm.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kmm.android.test.TestListViewModel

@Composable
fun TestListByTopic(
    topicId: String,
    navController: NavController,
    vm: TestListViewModel = viewModel()
) {
    val allTests by vm.tests.collectAsState(initial = emptyList())
    LaunchedEffect(topicId) { vm.fetchTests() }

    val filtered = remember(allTests, topicId) {
        allTests.filter { it.testId == topicId }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(filtered) { index, test ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .offset(x = 4.dp, y = 4.dp)
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(24.dp), clip = false)
                    .offset(x = (-4).dp, y = (-4).dp)
                    .clickable { navController.navigate("test/${test.id}") },
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Test num, zero-padded to two digits
                    Text(
                        text = "Test ${(index + 1).toString().padStart(2, '0')}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(4.dp))

                    // Test name
                    Text(
                        text = test.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}