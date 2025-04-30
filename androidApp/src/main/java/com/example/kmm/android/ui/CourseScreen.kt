package com.example.kmm.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.kmm.android.R
import com.example.kmm.android.auth.AuthViewModel
import com.example.kmm.android.auth.AuthViewModelFactory
import com.example.kmm.android.course.CourseViewModel
import com.example.kmm.course.Course

@Composable
fun CourseScreen(navController: NavController) {
    val courseViewModel: CourseViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
    val coursesState = courseViewModel.courseList.collectAsState()
    val courses = coursesState.value

    LaunchedEffect(Unit) { courseViewModel.fetchCourses() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // logout
        Row(
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(Modifier.height(16.dp))

        // Header + "see all"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Course",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "see all",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { /* TODO: see-all action */ }
            )
        }

        Spacer(Modifier.height(8.dp))

        // Description
        Text(
            text = "Ready to learn? Choose your course and start your journey! " +
                    "Select a topic that interests you and dive into a new adventure in coding",
            fontSize = 18.sp
        )

        Spacer(Modifier.height(24.dp))

        // Horizontal list of cards
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(courses) { course ->
                CourseCard(course = course) {
                    navController.navigate("course/${course.id}")
                }
            }
        }
    }
}

@Composable
private fun CourseCard(course: Course, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(width = 240.dp, height = 160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            // TODO: replace with real image loader (Coil/Glide) if you have images
            Image(
                painter = painterResource(id = R.drawable.language_1),
                contentDescription = "Language icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Level badge
            Text(
                text = course.level,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp) //hor 8.dp
//                    .background(
//                        color = Color(0x80000000),
//                        shape = RoundedCornerShape(12.dp)
//                    )
//                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .align(Alignment.TopStart)
            )

            // Favorite icon
            IconButton(
                onClick = { /* TODO: toggle favorite */ },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
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
                    text = course.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = course.specification,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}