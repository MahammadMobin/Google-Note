package com.example.death_note.gemini

data class GeminiResponse(
    val candidates: List<Candidate>? = null
)

data class Candidate(
    val content: Content? = null
)

data class Content(
    val parts: List<Part>? = null
)

data class Part(
    val text: String? = null
)

data class GeminiRequest(
    val contents: List<ContentRequest>? = null
)

data class ContentRequest(
    val parts: List<PartRequest>? = null
)

data class PartRequest(
    val text: String
)
