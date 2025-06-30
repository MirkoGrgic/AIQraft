package com.example.aiqraft10.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.aiqraft10.data.QuestionResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerReviewScreen(
    questionResults: List<QuestionResult>,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Answers") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF081528),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFCFDCF3)
    ) { paddingValues ->
        if (questionResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No answers to review.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(questionResults) { index, result ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Question ${index + 1}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF081528)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(result.questionText, fontSize = 16.sp)

                            Spacer(modifier = Modifier.height(12.dp))

                            result.options.forEachIndexed { i, optionText ->
                                val letter = ('A' + i).toString()
                                val isCorrect = result.correctAnswer == optionText
                                val isSelected = result.selectedAnswer == optionText

                                val backgroundColor = when {
                                    isCorrect -> Color(0xFFDFF5DD)
                                    isSelected && !isCorrect -> Color(0xFFFFEBEE)
                                    else -> Color.Transparent
                                }

                                val borderColor = when {
                                    isCorrect -> Color(0xFF4CAF50)
                                    isSelected && !isCorrect -> Color(0xFFF44336)
                                    else -> Color.Gray
                                }

                                val icon = when {
                                    isCorrect -> Icons.Default.Check
                                    isSelected && !isCorrect -> Icons.Default.Close
                                    else -> null
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                                        .border(1.dp, borderColor, shape = RoundedCornerShape(8.dp))
                                        .padding(8.dp)
                                ) {
                                    Text("$letter. $optionText", modifier = Modifier.weight(1f))
                                    if (icon != null) {
                                        Icon(icon, contentDescription = null, tint = borderColor)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val wasCorrect = result.wasCorrect
                                val verdictColor = if (wasCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)
                                val verdictText = if (wasCorrect) "Correct Answer" else "Wrong Answer"

                                Icon(
                                    imageVector = if (wasCorrect) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = verdictColor
                                )
                                Text(
                                    verdictText,
                                    color = verdictColor,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}