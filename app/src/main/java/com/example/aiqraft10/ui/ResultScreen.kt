package com.example.aiqraft10.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import java.util.Locale
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.navigation.NavController

@Composable
fun ResultScreen(
    score: Int,
    correct: Int,
    wrong: Int,
    duration: Int,
    totalQuestions: Int,
    onViewAnswers: () -> Unit,
    onCheckProgress: () -> Unit,
    onReattemptQuiz: () -> Unit,
    navController: NavController
) {
    val timeFormatted = String.format(Locale.US, "%02d:%02d", duration / 60, duration % 60)
    val percentageScore = ((score.toFloat() / totalQuestions) * 100).toInt()

    BackHandler {
        navController.navigate("home") {
            popUpTo("result") { inclusive = true }
            launchSingleTop = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCFDCF3))
            .padding(bottom = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF081528)),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("home") {
                            popUpTo("result") { inclusive = true }
                            launchSingleTop = true
                        }
                    },
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
                    text = "Result",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }


        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBFE))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Score is:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(50.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$percentageScore",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1976D2)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBFE))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Time Taken", fontWeight = FontWeight.SemiBold)
                        Text(timeFormatted)
                    }
                    Column {
                        Text("Total Questions", fontWeight = FontWeight.SemiBold)
                        Text("$totalQuestions")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBFE))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF4CAF50))
                        Text("Correct", fontWeight = FontWeight.Medium)
                        Text("$correct", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBFE))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = null, tint = Color(0xFFF44336))
                        Text("Wrong", fontWeight = FontWeight.Medium)
                        Text("$wrong", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCheckProgress() },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBFE))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ShowChart, contentDescription = null, tint = Color(0xFF1976D2))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Check Your Progress", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { onReattemptQuiz() },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                contentPadding = PaddingValues(vertical = 0.dp)
            ) {
                Text("RE-ATTEMPT", color = Color.White, fontSize = 16.sp)
            }
            Button(
                onClick = onViewAnswers,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF081528)),
                contentPadding = PaddingValues(vertical = 0.dp)
            ) {
                Text("VIEW ANSWERS", color = Color.White, fontSize = 16.sp)
            }
        }

    }
}