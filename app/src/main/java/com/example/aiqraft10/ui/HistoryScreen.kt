package com.example.aiqraft10.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aiqraft10.data.QuizResult
import com.example.aiqraft10.viewmodel.QuizViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: QuizViewModel
) {
    var quizResults by remember { mutableStateOf<List<QuizResult>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.getUserQuizResults { results ->
            quizResults = results.sortedByDescending { it.timestamp }.take(10)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
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
                    "Quiz History",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            itemsIndexed(quizResults) { index, result ->
                HistoryItem(result = result) {
                    navController.navigate("history_answers/$index")
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun HistoryItem(result: QuizResult, onClick: () -> Unit) {
    val formattedDate = remember(result.timestamp) {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        sdf.format(Date(result.timestamp))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Topic: ${result.topic}", fontWeight = FontWeight.Bold)
            Text("Date: $formattedDate")
        }
    }
}