package com.example.aiqraft10.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import com.example.aiqraft10.R
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    onStartQuiz: (String, Int) -> Unit,
    navController: NavController? = null
) {
    var topic by remember { mutableStateOf("") }
    var questionCount by remember { mutableStateOf("5") }
    var errorMessage by remember { mutableStateOf("") }

    val darkBlue = Color(0xFF081528)
    val teal = Color(0xFF00ADB5)
    val offWhite = Color(0xFFFFFBFE)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(450.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(teal.copy(alpha = 0.4f), offWhite.copy(alpha = 0.2f))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "Start a New Quiz",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = topic,
                    onValueChange = { topic = it; errorMessage = "" },
                    label = { Text("Topic", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = teal,
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = teal
                    ),
                    textStyle = LocalTextStyle.current.copy(color = Color.White)
                )

                OutlinedTextField(
                    value = questionCount,
                    onValueChange = {
                        questionCount = it
                        errorMessage = ""
                    },
                    label = { Text("Number of questions (1-15)", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = teal,
                        unfocusedBorderColor = Color.LightGray,
                        cursorColor = teal
                    ),
                    textStyle = LocalTextStyle.current.copy(color = Color.White)
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val count = questionCount.toIntOrNull()

                        if (topic.isBlank()) {
                            errorMessage = "Topic cannot be empty."
                        } else if (count == null || count !in 1..15) {
                            errorMessage = "Number of questions must be between 1 and 15."
                        } else {
                            errorMessage = ""
                            onStartQuiz(topic.trim(), count)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = teal)
                ) {
                    Text("Start Quiz", color = darkBlue,fontWeight = FontWeight.Black,)
                }
            }
        }

        navController?.let {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .size(28.dp)
                    .clickable { navController.navigate("home") }
            )
        }
    }
}