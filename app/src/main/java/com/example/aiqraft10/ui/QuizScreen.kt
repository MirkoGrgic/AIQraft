package com.example.aiqraft10.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aiqraft10.R
import com.example.aiqraft10.data.Question
import com.example.aiqraft10.data.QuestionResult
import com.example.aiqraft10.viewmodel.QuizViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun QuizScreen(
    questions: List<Question>,
    viewModel: QuizViewModel = hiltViewModel(),
    navController: NavController,
    topic: String
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var correctAnswers by remember { mutableIntStateOf(0) }
    var finished by remember { mutableStateOf(false) }
    var timerStarted by remember { mutableStateOf(false) }
    var answered by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val questionResults = remember { mutableStateListOf<QuestionResult>() }

    val darkBlue = Color(0xFF081528)
    val backgroundColor = Color(0xFFCFDCF3)

    if (finished) {
        viewModel.stopTimer()

        viewModel.correctAnswers = correctAnswers
        viewModel.wrongAnswers = questions.size - correctAnswers

        viewModel.saveResult(
            topic = topic,
            total = questions.size,
            correct = correctAnswers,
            wrong = questions.size - correctAnswers,
            time = viewModel.duration,
            questionResults = questionResults
        )

        viewModel.lastQuestionResults = questionResults.toList()

        navController.navigate("result") {
            popUpTo("start") { inclusive = false }
            launchSingleTop = true
        }
    } else if (questions.isNotEmpty()) {
        val currentQuestion = questions[currentIndex]

        LaunchedEffect(currentIndex) {
            answered = false
            if (!timerStarted && currentIndex == 0) {
                viewModel.startTimer()
                timerStarted = true
            }
        }

        val minutes = viewModel.duration / 60
        val seconds = viewModel.duration % 60

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(darkBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = String.format(Locale.US, "%d:%02d", minutes, seconds),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Question ${currentIndex + 1} of ${questions.size}",
                    style = MaterialTheme.typography.titleMedium,
                    color = darkBlue
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(24.dp))

                currentQuestion.answers.forEach { answer ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable(enabled = !answered) {
                                answered = true
                                val wasCorrect = answer.isTrue
                                if (wasCorrect) correctAnswers++

                                questionResults.add(
                                    QuestionResult(
                                        questionText = currentQuestion.question,
                                        options = currentQuestion.answers.map { it.text },
                                        selectedAnswer = answer.text,
                                        correctAnswer = currentQuestion.answers.first { it.isTrue }.text,
                                        wasCorrect = wasCorrect
                                    )
                                )

                                coroutineScope.launch {
                                    delay(400)
                                    if (currentIndex + 1 < questions.size) {
                                        currentIndex++
                                    } else {
                                        finished = true
                                    }
                                }
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = answer.text,
                                style = MaterialTheme.typography.bodyLarge,
                                color = darkBlue
                            )
                        }
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFCFDCF3))
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loading),
                    contentDescription = "Loading Image",
                    modifier = Modifier
                        .size(380.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Generating quiz...",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF081528)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    modifier = Modifier
                        .width(300.dp)
                        .height(6.dp),
                    color = Color(0xFF081528)
                )
            }
        }
    }
}