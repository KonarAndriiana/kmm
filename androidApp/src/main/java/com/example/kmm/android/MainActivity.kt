package com.example.kmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kmm.android.ui.CourseScreen
import com.example.kmm.android.ui.LoginScreen
import com.example.kmm.android.ui.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginScreen(navController) }
                composable("register") { RegisterScreen(navController) }
                composable("courseList") { CourseScreen(navController, null) }
                composable("course/{courseId}") { backStackEntry ->
                    val courseId = backStackEntry.arguments?.getString("courseId")?.toIntOrNull()
                    CourseScreen(navController, courseId)
                }
            }
        }
    }
}