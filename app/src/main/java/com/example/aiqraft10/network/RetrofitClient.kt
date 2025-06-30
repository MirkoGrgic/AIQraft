package com.example.aiqraft10.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://api.perplexity.ai/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer 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")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

    val apiService: PerplexityApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PerplexityApiService::class.java)
    }
}