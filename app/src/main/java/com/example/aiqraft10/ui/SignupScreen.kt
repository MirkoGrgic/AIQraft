package com.example.aiqraft10.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aiqraft10.R
import com.example.aiqraft10.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController, onSuccess: () -> Unit) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val auth = remember { AuthRepository(firebaseAuth) }

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val isFormValid = username.isNotBlank() && email.isNotBlank() && password.isNotBlank()

    val darkBlue = Color(0xFF081528)
    val offWhite = Color(0xFFFFFBFE)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background3),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.signup),
                contentDescription = "Signup image",
                modifier = Modifier
                    .height(260.dp)
                    .padding(bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(offWhite.copy(alpha = 0.1f),offWhite.copy(alpha = 0.1f), offWhite.copy(alpha = 0.4f))
                        )
                    )
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Sign Up",
                        fontSize = 26.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username", color = Color.White) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = offWhite,
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = offWhite
                        ),
                        textStyle = LocalTextStyle.current.copy(color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email", color = Color.White) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = offWhite,
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = offWhite
                        ),
                        textStyle = LocalTextStyle.current.copy(color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = Color.White) },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = offWhite,
                            unfocusedBorderColor = Color.LightGray,
                            cursorColor = offWhite
                        ),
                        textStyle = LocalTextStyle.current.copy(color = Color.White)
                    )

                    error?.let {
                        Spacer(Modifier.height(8.dp))
                        Text(it, color = Color.Red, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (username.isBlank()) {
                                error = "Username is required"
                                return@Button
                            }
                            auth.signup(username, email, password) { success, message ->
                                if (success) {
                                    onSuccess()
                                } else {
                                    error = message ?: "Signup failed"
                                }
                            }
                        },
                        enabled = isFormValid,
                        colors = ButtonDefaults.buttonColors(containerColor = offWhite.copy(alpha=0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign Up", fontWeight = FontWeight.Bold, color = darkBlue)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(onClick = { navController.navigate("login") }) {
                        Text("Already have an account? Login", color = Color.White)
                    }
                }
            }
        }
    }
}