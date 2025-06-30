package com.example.aiqraft10.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aiqraft10.R
import com.example.aiqraft10.viewmodel.QuizViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun HomeScreen(
    navController: NavController,
    quizViewModel: QuizViewModel
) {
    var username by remember { mutableStateOf("") }

    val darkBlue = Color(0xFF081528)

    LaunchedEffect(Unit) {
        quizViewModel.getCurrentUsernameFromResults {
            username = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCFDCF3))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(darkBlue,darkBlue,darkBlue.copy(alpha = 0.9f),darkBlue.copy(alpha = 0.5f))
                    )
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Column {
                    Text(
                        text = "Welcome,",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = username,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .clickable { navController.navigate("account") },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier.size(38.dp)
                    )
                }
            }


            Image(
                painter = painterResource(id = R.drawable.name),
                contentDescription = "AIQraft Logo",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(100.dp)
                    .padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HomeActionCard(
                iconId = R.drawable.baseline_history_24,
                label = "History",
                onClick = { navController.navigate("history") }
            )

            HomeActionCard(
                iconId = R.drawable.baseline_graph_24,
                label = "Progress",
                onClick = { navController.navigate("progress") }
            )

            HomeActionCard(
                iconId = R.drawable.baseline_logout_24,
                label = "Logout",
                onClick = {
                    quizViewModel.logout()
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(darkBlue.copy(alpha = 0.3f), darkBlue.copy(alpha = 0.7f), darkBlue.copy(alpha = 0.9f), darkBlue.copy(alpha = 0.3f)
                        )
                    )
                )
                .clickable { navController.navigate("helpAndTips") },
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Help",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "   &",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Text(
                        text = "Tips",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }


                Image(
                    painter = painterResource(id = R.drawable.help),
                    contentDescription = "Help Illustration",
                    modifier = Modifier
                        .height(200.dp)
                        .aspectRatio(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(darkBlue.copy(alpha = 0.5f),darkBlue.copy(alpha = 0.9f),darkBlue.copy(alpha = 0.9f),darkBlue.copy(alpha = 0.5f))
                    )
                )
                .clickable { navController.navigate("start") },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Start Quiz",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun HomeActionCard(
    iconId: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = label,
            modifier = Modifier.size(40.dp),
            tint = Color(0xFF081528)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF081528)
        )
    }
}