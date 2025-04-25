package com.example.kmm.android.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kmm.android.R
import com.example.kmm.android.auth.AuthViewModel
import com.example.kmm.android.auth.AuthViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val loginState by authViewModel.loginState.collectAsState()

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val registrationSuccess = authViewModel.showRegistrationSuccess.value
    val registrationMessage = authViewModel.getRegistrationSuccessMessage()

    val resetSuccess = authViewModel.showResetSuccess.value
    val resetMessage = authViewModel.getResetSuccessMessage()

    // Bottom sheet
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // Forgot password
    var resetEmail by remember { mutableStateOf("") }
    var resetError by remember { mutableStateOf<String?>(null) }

    // Login background img
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.login_register_background),
            contentDescription = "Login Background",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = 0.99f }
                .blur(8.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            // Error - Success Pill
            if ((showError && errorMessage != null) || registrationSuccess || resetSuccess) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25))
                        .background(
                            when {
                                registrationSuccess || resetSuccess -> Color(0xFFB9F6CA)
                                else -> Color.White
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when {
                            registrationSuccess -> registrationMessage
                            resetSuccess -> resetMessage
                            else -> errorMessage!!
                        },
                        color = when {
                            registrationSuccess || resetSuccess -> Color(0xFF1B5E20)
                            else -> Color.Red
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            .semantics { contentDescription = "error_msg" }
                    )
                }

                // Show error - success msg for 5sec
                LaunchedEffect(registrationSuccess, resetSuccess, errorMessage) {
                    delay(5000)
                    showError = false
                    errorMessage = null
                    if (registrationSuccess) authViewModel.markRegistrationSuccessShown()
                    if (resetSuccess) authViewModel.markResetSuccessShown()
                }
            }

            // Form
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.semantics { contentDescription = "welcome_text" }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = buildAnnotatedString {
                        append("back to ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Bugless")
                        }
                    },
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.semantics { contentDescription = "buggless_text" }
                )

                Spacer(modifier = Modifier.height(96.dp))

                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        showError = false
                    },
                    placeholder = {
                        Text(
                            text = "email",
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 14.sp
                        )
                    },
                    textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(50))
                        .semantics { contentDescription = "email_input" },
                    shape = RoundedCornerShape(50),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        cursorColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        showError = false
                    },
                    placeholder = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 36.dp)
                        ) {
                            Text(
                                text = "password",
                                color = Color.Gray,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 14.sp
                            )
                        }
                    },
                    textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(50))
                        .semantics { contentDescription = "password_input" },
                    shape = RoundedCornerShape(50),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon =
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier.semantics { contentDescription = "toggle_pass" }
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Toggle Password Visibility",
                                tint = Color.Gray
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black,
                        cursorColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Forgot password
                Text(
                    text = "Forgot password?",
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clickable { showSheet = true }
                        .semantics { contentDescription = "forgot_btn" }
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Log In button
                OutlinedButton(
                    onClick = {
                        authViewModel.login(email, password)
                    },
                    modifier = Modifier
                        .width(120.dp)
                        .height(48.dp)
                        .align(Alignment.CenterHorizontally)
                        .semantics { contentDescription = "login_btn" },
                    shape = RoundedCornerShape(25),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    border = BorderStroke(3.dp, Color.White)
                ) {
                    Text("Log In", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(72.dp))

                Row {
                    Text("Don't have an account? ",
                        fontSize = 14.sp,
                        color = Color.White,
                        modifier = Modifier.semantics { contentDescription = "sign_up_text" }
                    )
                    Text(
                        text = "Sign up now",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        style = LocalTextStyle.current.copy(textDecoration = TextDecoration.Underline),
                        modifier = Modifier
                            .clickable { navController.navigate("register") }
                            .semantics { contentDescription = "sign_up_redirect" }
                    )
                }
            }

            // Modal Bottom Sheet
            if (showSheet) {
                LaunchedEffect(showSheet) {
                    sheetState.expand()
                }

                ModalBottomSheet(
                    onDismissRequest = { showSheet = false },
                    sheetState = sheetState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = screenHeight * 0.1f),
                    containerColor = Color.White,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Forgot password",
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            modifier = Modifier.semantics { contentDescription = "forgot_pass_text" }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Enter your email, and we’ll send you\nlink and instructions to reset your password.",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier.semantics { contentDescription = "info_text" }
                        )

                        Spacer(modifier = Modifier.height(72.dp))

                        OutlinedTextField(
                            value = resetEmail,
                            onValueChange = { resetEmail = it },
                            placeholder = {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "email",
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(50))
                                .semantics { contentDescription = "email_input" },
                            shape = RoundedCornerShape(50),
                            textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 14.sp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                unfocusedTextColor = Color.Black,
                                focusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            )
                        )

                        if (resetError != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = resetError!!,
                                color = Color.Red,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.semantics { contentDescription = "error_msg" }
                            )
                        }

                        Spacer(modifier = Modifier.height(48.dp))

                        // Reset Button (Modal Bottom Sheet)
                        Button(
                            onClick = {
                                authViewModel.resetPassword(
                                    email = resetEmail,
                                    onSuccess = {
                                        coroutineScope.launch {
                                            resetError = null
                                            sheetState.hide()
                                            showSheet = false
                                        }
                                    },
                                    onError = { error ->
                                        coroutineScope.launch {
                                            resetError = error
                                            showError = true
                                            errorMessage = error
                                            sheetState.hide()
                                            showSheet = false
                                        }
                                    }
                                )
                            },
                            modifier = Modifier
                                .width(120.dp)
                                .height(48.dp)
                                .semantics { contentDescription = "reset_btn" },
                            shape = RoundedCornerShape(25),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Reset", fontSize = 16.sp)
                        }
                    }
                }
            }

            // Handle login state + set appropriate errors
            LaunchedEffect(loginState) {
                loginState?.let { message ->
                    if (message.contains("✅")) {
                        navController.navigate("courseList")
                    } else if (message.contains("❌")) {
                        showError = true
                        errorMessage = message.replace("❌ ", "")
                    }
                }
            }
        }
    }
}