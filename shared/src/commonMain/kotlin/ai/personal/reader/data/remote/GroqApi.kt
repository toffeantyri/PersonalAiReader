package ai.personal.reader.data.remote

import ai.personal.reader.data.remote.dto.GroqRequest
import ai.personal.reader.data.remote.dto.GroqResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class GroqApi(private val httpClient: HttpClient) {

    private val apiKey = "YOUR_GROQ_API_KEY" // TODO: Move to a secure place

    suspend fun getCompletion(request: GroqRequest): GroqResponse {
        return httpClient.post("https://api.groq.com/openai/v1/chat/completions") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $apiKey")
            setBody(request)
        }.body()
    }
} 