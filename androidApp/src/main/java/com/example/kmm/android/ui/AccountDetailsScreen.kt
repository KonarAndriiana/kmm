package com.example.kmm.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.kmm.android.auth.AuthViewModel
import com.example.kmm.android.auth.AuthViewModelFactory
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import java.io.File

@Composable
fun AccountDetailsScreen(
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())
) {
    val firstName by authViewModel.firstName.collectAsState()
    val lastName  by authViewModel.lastName.collectAsState()
    val email     by authViewModel.emailAddr.collectAsState()
    val context = LocalContext.current
    val uid = Firebase.auth.currentUser?.uid
    val file = uid?.let { File(context.filesDir, "${it}_profile.jpg") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(32.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(Modifier.height(24.dp))

        // profile photo
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(112.dp)
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

        Spacer(Modifier.height(100.dp))

        @Composable
        fun labeledField(label: String, value: String) {
            Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(Modifier.height(16.dp))
        }

        labeledField("First name", firstName)
        labeledField("Last name",  lastName)
        labeledField("Email",      email)
        labeledField("Password",  "••••••••")
    }
}