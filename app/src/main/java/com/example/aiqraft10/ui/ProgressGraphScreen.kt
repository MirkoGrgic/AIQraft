package com.example.aiqraft10.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aiqraft10.data.QuizResult
import com.example.aiqraft10.viewmodel.QuizViewModel
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData



@Composable
fun ProgressGraphScreen(viewModel: QuizViewModel = hiltViewModel(), navController: NavController) {
    var results by remember { mutableStateOf<List<QuizResult>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.getUserQuizResults {
            results = it.sortedBy { r -> r.timestamp }
        }
    }

    val bgColor = Color(0xFFCFDCF3)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bgColor
    ) {
        Column {
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
                        "Progress Overview",
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
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                if (results.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }else {
                    val topTopics = results
                        .groupBy { it.topic }
                        .mapValues { (_, list) ->
                            list.map { it.correctAnswers.toFloat() / it.totalQuestions }.average()
                        }
                        .entries.sortedByDescending { it.value }
                        .take(5)

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Accuracy per Topic", color = Color.Black)
                            BarChart(
                                barChartData = BarChartData(
                                    bars = topTopics.map { (topic, acc) ->
                                        BarChartData.Bar(
                                            label = if (topic.length > 6) topic.take(6) + "…" else topic,
                                            value = acc.toFloat(),
                                            color = Color(0xFF4FC3F7)
                                        )
                                    }
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                animation = simpleChartAnimation(),
                                barDrawer = SimpleBarDrawer()
                            )
                        }
                    }

                    val totalCorrect = results.sumOf { it.correctAnswers }
                    val totalQuestions = results.sumOf { it.totalQuestions }
                    val totalWrong = totalQuestions - totalCorrect

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Correct vs Incorrect", color = Color.Black)

                            PieChart(
                                pieChartData = PieChartData(
                                    slices = listOf(
                                        PieChartData.Slice(
                                            value = totalCorrect.toFloat(),
                                            color = Color(0xFF66BB6A)
                                        ),
                                        PieChartData.Slice(
                                            value = totalWrong.toFloat(),
                                            color = Color(0xFFEF5350)
                                        )
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                animation = simpleChartAnimation()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Correct", color = Color(0xFF66BB6A))
                                    Text("$totalCorrect / $totalQuestions", color = Color.Black)
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("Incorrect", color = Color(0xFFEF5350))
                                    Text("$totalWrong / $totalQuestions", color = Color.Black)
                                }
                            }
                        }
                    }

                    val recentResults = results.takeLast(7)

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Accuracy Over Time", color = Color.Black)
                            LineChart(
                                linesChartData = listOf(
                                    LineChartData(
                                        points = recentResults.map {
                                            LineChartData.Point(
                                                value = it.correctAnswers.toFloat() / it.totalQuestions,
                                                label = if (it.topic.length > 6) it.topic.take(6) + "..." else it.topic

                                            )
                                        },
                                        lineDrawer = SolidLineDrawer(color = Color(0xFF42A5F5))
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                animation = simpleChartAnimation(),
                                yAxisDrawer = SimpleYAxisDrawer(
                                    labelTextColor = Color.Gray,
                                    labelTextSize = 12.sp,
                                    axisLineColor = Color.Gray
                                )
                            )
                        }
                    }

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Duration per Quiz", color = Color.Black)
                            LineChart(
                                linesChartData = listOf(
                                    LineChartData(
                                        points = recentResults.map {
                                            LineChartData.Point(
                                                value = it.durationSeconds.toFloat(),
                                                label = if (it.topic.length > 6) it.topic.take(6) + "..." else it.topic

                                            )
                                        },
                                        lineDrawer = SolidLineDrawer(color = Color(0xFFFFA726))
                                    )
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                animation = simpleChartAnimation(),
                                yAxisDrawer = SimpleYAxisDrawer(
                                    labelTextColor = Color.Gray,
                                    labelTextSize = 12.sp,
                                    axisLineColor = Color.Gray
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}