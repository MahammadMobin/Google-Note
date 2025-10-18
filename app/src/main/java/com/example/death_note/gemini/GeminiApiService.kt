package com.example.death_note.gemini

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface GeminiApiService {

    @POST("v1beta/models/{modelName}:generateContent")
    suspend fun generateContent(
        @Path("modelName") modelName: String,
        @Body request: GeminiRequest
    ): Response<GeminiResponse>
}
