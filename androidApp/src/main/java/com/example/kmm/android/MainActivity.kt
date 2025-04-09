package com.example.kmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kmm.android.auth.AuthViewModel
import com.example.kmm.android.auth.AuthViewModelFactory
import com.example.kmm.android.ui.CourseScreen
import com.example.kmm.android.ui.LoginScreen
import com.example.kmm.android.ui.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val viewModelFactory = remember { AuthViewModelFactory() }
            val authViewModel: AuthViewModel = viewModel(factory = viewModelFactory)

            val isLoggedIn by authViewModel.isUserLoggedIn
            val startDestination = if (isLoggedIn) "courseList" else "login"

            NavHost(navController = navController, startDestination = startDestination) {
                composable("login") {
                    LoginScreen(navController = navController, authViewModel = authViewModel)
                }
                composable("register") {
                    RegisterScreen(navController = navController, authViewModel = authViewModel)
                }
                composable("courseList") {
                    CourseScreen(navController, null)
                }
                composable("course/{courseId}") { backStackEntry ->
                    val courseId = backStackEntry.arguments?.getString("courseId")?.toIntOrNull()
                    CourseScreen(navController, courseId)
                }
            }
        }
    }
}