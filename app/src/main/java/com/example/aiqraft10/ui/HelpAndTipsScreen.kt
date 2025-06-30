package com.example.aiqraft10.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HelpAndTipsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCFDCF3))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF081528)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    "Help & Tips",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = "Welcome to AIQraft!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF081528)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("What is AIQraft?\n")
                    }
                    append("AIQraft is an AI-powered quiz application designed to help you learn and test your knowledge across various topics through intelligent quizzes.")
                },
                fontSize = 16.sp,
                color = Color(0xFF081528),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("How to Use:\n")
                    }
                    append("• Choose a topic and number of questions from the Start screen.\n")
                    append("• Complete the quiz and review your answers afterward.\n")
                    append("• Visit the History and Progress sections to monitor your learning.")
                },
                fontSize = 16.sp,
                color = Color(0xFF081528),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Tips:\n")
                    }
                    append("• Practice consistently to build strong understanding.\n")
                    append("• Use the View Answers option to learn from your mistakes.\n")
                    append("• Analyze your performance on the Progress screen.")
                },
                fontSize = 16.sp,
                color = Color(0xFF081528),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "For support or questions, please contact our team anytime.",
                fontSize = 16.sp,
                color = Color(0xFF081528)
            )
        }
    }
}
