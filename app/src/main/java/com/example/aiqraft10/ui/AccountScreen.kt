package com.example.aiqraft10.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aiqraft10.R
import com.example.aiqraft10.data.QuizResult
import com.example.aiqraft10.viewmodel.QuizViewModel
import kotlin.math.roundToInt

@Composable
fun AccountScreen(
    navController: NavController,
    viewModel: QuizViewModel
) {
    var results by remember { mutableStateOf<List<QuizResult>>(emptyList()) }
    var username by remember { mutableStateOf("") }
    var memberSince by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getUserQuizResults { list -> results = list }
        viewModel.getCurrentUsernameFromResults { username = it }
        viewModel.getMemberSince { date -> memberSince = date }
    }

    val totalQuizzes = results.size
    val totalSeconds = results.sumOf { it.durationSeconds }
    val totalMinutes = totalSeconds / 60
    val totalHours = totalMinutes / 60
    val averageCorrect = if (results.isNotEmpty()) results.sumOf { it.correctAnswers }.toDouble() / totalQuizzes else 0.0
    val bestScore = results.maxOfOrNull { it.correctAnswers } ?: 0
    val averageTimePerQuiz = if (totalQuizzes > 0) totalSeconds / totalQuizzes else 0
    val avgMinutes = averageTimePerQuiz / 60
    val avgSeconds = averageTimePerQuiz % 60

    val darkBlue = Color(0xFF081528)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCFDCF3))
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(darkBlue),
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
                    "Account",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(darkBlue.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "User Icon",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = username,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = darkBlue
            )

            Text(
                text = "AIQraft User",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Your Stats",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = darkBlue,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            content = {
                item { StatsCard(label = "Total Quizzes", value = totalQuizzes.toString(), icon = Icons.Default.List) }
                item { StatsCard(label = "Time Spent", value = if (totalHours > 0) "$totalHours h ${totalMinutes % 60} min" else "$totalMinutes min", icon = Icons.Default.Timer) }
                item { StatsCard(label = "Avg Correct", value = "${averageCorrect.roundToInt()}", icon = Icons.Default.CheckCircle) }
                item { StatsCard(label = "Best Score", value = bestScore.toString(), icon = Icons.Default.Star) }
                item { StatsCard(label = "Avg Time/Q", value = "$avgMinutes min $avgSeconds sec", icon = Icons.Default.Speed) }
                item { StatsCard(label = "Member Since", value = memberSince, icon = Icons.Default.DateRange) }
            }
        )
    }
}

@Composable
fun StatsCard(label: String, value: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(icon, contentDescription = label, tint = Color(0xFF081528), modifier = Modifier.size(32.dp))
            Column {
                Text(
                    text = label,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color(0xFF444444)
                )
                Text(
                    text = value,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF081528)
                )
            }
        }
    }
}