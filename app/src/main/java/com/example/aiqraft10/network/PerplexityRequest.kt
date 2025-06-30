package com.example.aiqraft10.network

data class PerplexityRequest(
    val model: String = "sonar-pro",
    val messages: List<Message>
) {
    data class Message(
        val role: String,
        val content: String
    )
}