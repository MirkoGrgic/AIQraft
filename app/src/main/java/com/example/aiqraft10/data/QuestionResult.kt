package com.example.aiqraft10.data


data class QuestionResult(
    val questionText: String = "",
    val options: List<String> = emptyList(),
    val selectedAnswer: String = "",
    val correctAnswer: String = "",
    val wasCorrect: Boolean = false
)