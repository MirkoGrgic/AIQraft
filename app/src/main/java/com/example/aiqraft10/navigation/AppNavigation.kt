package com.example.aiqraft10.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aiqraft10.ui.AccountScreen
import com.example.aiqraft10.ui.AnswerReviewScreen
import com.example.aiqraft10.ui.HelpAndTipsScreen
import com.example.aiqraft10.ui.HistoryScreen
import com.example.aiqraft10.ui.HomeScreen
import com.example.aiqraft10.ui.LoginScreen
import com.example.aiqraft10.ui.ProgressGraphScreen
import com.example.aiqraft10.ui.QuizScreen
import com.example.aiqraft10.ui.ResultScreen
import com.example.aiqraft10.ui.SignupScreen
import com.example.aiqraft10.ui.StartScreen
import com.example.aiqraft10.viewmodel.QuizViewModel

@Composable
fun AppNavigation(viewModel: QuizViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("home") {
            HomeScreen(navController = navController, quizViewModel = viewModel)
        }
        composable("start") {
            StartScreen(
                onStartQuiz = { topic, questionCount ->
                    viewModel.resetQuizState()
                    viewModel.generateQuiz(topic, questionCount)
                    navController.navigate("quiz/${topic}") {
                        popUpTo("start") { inclusive = true }
                    }
                },
                navController = navController
            )
        }
        composable(
            "quiz/{topic}",
            arguments = listOf(navArgument("topic") { type = NavType.StringType })
        ) { backStackEntry ->
            val topic = backStackEntry.arguments?.getString("topic")!!

            QuizScreen(
                questions = viewModel.quizQuestions,
                viewModel = viewModel,
                navController = navController,
                topic = topic
            )
        }

        composable("login") {
            LoginScreen(navController = navController, onSuccess = { navController.navigate("home") })
        }

        composable("signup") {
            SignupScreen(navController = navController, onSuccess = { navController.navigate("home") })
        }
        composable("result") {
            ResultScreen(
                score = viewModel.correctAnswers,
                correct = viewModel.correctAnswers,
                wrong = viewModel.wrongAnswers,
                duration = viewModel.duration,
                totalQuestions = viewModel.quizQuestions.size,
                onViewAnswers = {
                    navController.navigate("answers")  {
                        launchSingleTop = true
                        restoreState = false
                        popUpTo("result") {
                            inclusive = false
                        }
                    }
                },
                onCheckProgress = {
                    navController.navigate("progress")
                },
                onReattemptQuiz = {
                    navController.navigate("start") {
                        popUpTo("result") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                navController = navController
            )
        }
        composable("answers") {
            AnswerReviewScreen(
                questionResults = viewModel.lastQuestionResults,
                navController = navController
            )
        }
        composable("progress") {
            ProgressGraphScreen(navController = navController)
        }

        composable("history") {
            HistoryScreen(navController = navController, viewModel = viewModel)
        }

        composable("account") {
            AccountScreen(navController = navController, viewModel = viewModel)
        }

        composable(
            "history_answers/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            val result = viewModel.previousResults.getOrNull(index)

            if (result != null) {
                AnswerReviewScreen(
                    questionResults = result.questions,
                    navController = navController
                )
            }
        }
        composable("helpAndTips") {
            HelpAndTipsScreen(navController = navController)
        }
    }
}