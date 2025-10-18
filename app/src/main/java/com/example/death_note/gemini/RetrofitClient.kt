package com.example.death_note.gemini

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://generativelanguage.googleapis.com/"
    private const val API_KEY = "AIzaSyCXYcyLRme1hfZF7YHtwPrm77fxSKYe1c0" // <<<--- IMPORTANT: PASTE YOUR API KEY HERE

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val newUrl = original.url.newBuilder()
                .addQueryParameter("key", API_KEY)
                .build()
            val requestBuilder = original.newBuilder().url(newUrl)
            chain.proceed(requestBuilder.build())
        }
        .build()

    val instance: GeminiApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GeminiApiService::class.java)
    }
}