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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.kmm.android.R
import com.example.kmm.android.auth.AuthViewModel
import com.example.kmm.android.auth.AuthViewModelFactory
import com.example.kmm.android.course.CourseViewModel
import com.example.kmm.course.Course
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import java.io.File

@Composable
fun CourseScreen(navController: NavController) {
    val courseViewModel: CourseViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
    val courses by courseViewModel.courseList.collectAsState()
    val firstName by authViewModel.firstName.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        // load courses
        courseViewModel.fetchCourses()
        // preload drawables
        context.imageLoader.enqueue(
            ImageRequest.Builder(context)
                .data(R.drawable.language_1)
                .size(width = 240, height = 160)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()
        )
    }

    // look for the saved profile image
    val uid = Firebase.auth.currentUser?.uid
    val profileFile = uid?.let { File(context.filesDir, "${it}_profile.jpg") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Greeting + profile photo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 72.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment   = Alignment.CenterVertically
        ) {
            Text(
                text  = "Hi, $firstName 👋🏻",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            if (profileFile?.exists() == true) {
                Image(
                    painter            = rememberAsyncImagePainter(model = profileFile),
                    contentDescription = "Your profile photo",
                    modifier           = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { navController.navigate("profileMenu") },
                    contentScale       = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector       = Icons.Default.Image,
                    contentDescription= "Default avatar",
                    tint              = MaterialTheme.colorScheme.onBackground,
                    modifier          = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(8.dp)
                        .clickable { navController.navigate("profileMenu") }
                )
            }
        }

        // Header + "see all"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Course",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "see all",
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { /* TODO */ }
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

        // Horizontal list of Course cards
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(courses) { course ->
                CourseCard(course = course) {
                    navController.navigate("lectures/${course.id}")
                }
            }
        }

        // —— New “Test” section —— //

            Spacer(Modifier.height(48.dp))

            Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment   = Alignment.CenterVertically
            ) {
                Text(
                text = "Test",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
                )
                Text(
                    text = "see all",
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Ready to test your skills? Choose a test and see how much you've learned. " +
                        "Challenge yourself and level up!",
                fontSize = 18.sp
            )
        }
    }


@Composable
private fun CourseCard(course: Course, onClick: () -> Unit) {
    // Box to draw the offset shadow for course cards
    Box(
        modifier = Modifier
            .offset(x = 4.dp, y = 4.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
            .offset(x = (-4).dp, y = (-4).dp)
            .size(width = 240.dp, height = 160.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // turn off the built-in shadow
        ) {
            Box {
                // AsyncImage
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.language_1)
                        .size(width = 240, height = 160)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = "Programming Language Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Level badge
                Text(
                    text = course.level,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                )

                // Favorite icon
                IconButton(
                    onClick = { /* TODO */ },
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
                            .background(Color.White, RoundedCornerShape(12.dp))
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
}