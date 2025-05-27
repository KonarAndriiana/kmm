package com.example.kmm.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.kmm.android.auth.AuthViewModel
import com.example.kmm.android.auth.AuthViewModelFactory
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import java.io.File

@Composable
fun ProfileMenuScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
) {
    val context = LocalContext.current
    val uid = Firebase.auth.currentUser?.uid
    val file = uid?.let { File(context.filesDir, "${it}_profile.jpg") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        // profile Photo
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (file?.exists() == true) {
                Image(
                    painter = rememberAsyncImagePainter(file),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(Icons.Default.Image, contentDescription = null, tint = Color.Gray)
            }
        }

        Spacer(Modifier.height(96.dp))


        // menu
        data class MenuItem(val label: String, val onClick: () -> Unit)

        val items = listOf(
            MenuItem("Account")  { navController.navigate("accountDetails") },
            MenuItem("Settings") { /* no-op for now */              },
            MenuItem("Log Out")  {
                authViewModel.logout()
                navController.navigate("login") { popUpTo("courseList") { inclusive = true } }
            }
        )

        items.forEach { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(112.dp)
                    .padding(vertical = 16.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .border(
                        width = 4.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .clickable(onClick = item.onClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.label,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}