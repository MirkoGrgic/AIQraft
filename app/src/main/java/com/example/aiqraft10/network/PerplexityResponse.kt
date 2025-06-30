package com.example.aiqraft10.network

data class PerplexityResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class Message(
    val content: String
)
