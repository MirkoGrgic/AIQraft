package com.example.aiqraft10.data

data class QuizResult(
    val id: String = "",
    val userId: String = "",
    val username: String = "",
    val topic: String = "",
    val totalQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val incorrectAnswers: Int = 0,
    val durationSeconds: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val questions: List<QuestionResult> = emptyList()
)
