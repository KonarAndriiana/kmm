package com.example.kmm.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.kmm.android.R
import com.example.kmm.android.test.TestTopicViewModel

@Composable
fun TestsScreen(
    navController: NavController,
    viewModel: TestTopicViewModel = viewModel()
) {
    val topics by viewModel.topics.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTopics()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp)
    ) {
        Text(
            text = "Test",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .semantics { contentDescription = "test_text" }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(topics) { topic ->
                Box(
                    modifier = Modifier
                        .offset(x = 4.dp, y = 4.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
                        .offset(x = (-4).dp, y = (-4).dp)
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { navController.navigate("tests/${topic.id}") },
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Box {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(R.drawable.language_1)
                                    .size(width = 320, height = 200)
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            // Level badge
                            Text(
                                text = topic.level,
                                color = Color.White,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.TopStart)
                            )
                            // Favorite icon
                            IconButton(
                                onClick = { /* TODO */ },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .semantics { contentDescription = "like_btn" }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            // Bottom name + specification
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = topic.name,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(Modifier.height(12.dp))
                                Box(
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(12.dp))
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = topic.specification,
                                        color = Color.Black,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}