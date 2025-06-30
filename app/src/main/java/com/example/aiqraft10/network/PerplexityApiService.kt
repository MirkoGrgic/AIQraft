package com.example.aiqraft10.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PerplexityApiService {
    @POST("chat/completions")
    suspend fun generateQuestions(@Body request: PerplexityRequest): Response<PerplexityResponse>
}