package com.example.kmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kmm.android.auth.AuthViewModel
import com.example.kmm.android.auth.AuthViewModelFactory
import com.example.kmm.android.ui.AccountDetailsScreen
import com.example.kmm.android.ui.CourseScreen
import com.example.kmm.android.ui.CoursesScreen
import com.example.kmm.android.ui.LectureDetailsScreen
import com.example.kmm.android.ui.LectureListByCourse
import com.example.kmm.android.ui.LoginScreen
import com.example.kmm.android.ui.ProfileMenuScreen
import com.example.kmm.android.ui.RegisterScreen
import com.example.kmm.android.ui.SettingsScreen
import com.example.kmm.android.ui.TestDetailScreen
import com.example.kmm.android.ui.TestListByTopic
import com.example.kmm.android.ui.TestsScreen

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
                    CourseScreen(navController)
                }
                composable("allCourses") {
                    CoursesScreen(navController)
                }
                composable("tests") {
                    CourseScreen(navController)
                }
                composable("tests") {
                    TestsScreen(navController)
                }
                composable("profileMenu") {
                    ProfileMenuScreen(navController)
                }
                composable("accountDetails") {
                    AccountDetailsScreen()
                }
                composable("settings") {
                    SettingsScreen()
                }
                composable(
                    "lectures/{courseId}",
                    arguments = listOf(
                        navArgument("courseId") { type = NavType.StringType }
                    )
                ) { backStack ->
                    backStack.arguments?.getString("courseId")?.let { courseId ->
                        LectureListByCourse(
                            courseId = courseId,
                            navController = navController
                        )
                    }
                }

                composable(
                    "lecture/{lectureId}",
                    arguments = listOf(
                        navArgument("lectureId") { type = NavType.StringType }
                    )
                ) { backStack ->
                    backStack.arguments?.getString("lectureId")?.let { lectureId ->
                        LectureDetailsScreen(lectureId = lectureId)
                    }
                }

                composable(
                    "tests/{topicId}",
                    arguments = listOf(navArgument("topicId") { type = NavType.StringType })
                ) { backStack ->
                    backStack.arguments?.getString("topicId")?.let { topicId ->
                        TestListByTopic(
                            topicId = topicId,
                            navController = navController
                        )
                    }
                }

                composable(
                    "test/{testId}",
                    arguments = listOf(navArgument("testId") { type = NavType.StringType })
                ) { backStack ->
                    backStack.arguments?.getString("testId")?.let { testId ->
                        TestDetailScreen(testId = testId)
                    }
                }

            }
        }
    }
}