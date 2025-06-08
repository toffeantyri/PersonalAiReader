package ai.personal.reader.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GroqRequest(
    val messages: List<Message>,
    val model: String
)

@Serializable
data class Message(
    val role: String,
    val content: String
)

@Serializable
data class GroqResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    @SerialName("x_groq")
    val xGroq: XGroq?
)

@Serializable
data class Choice(
    val index: Int,
    val message: Message,
    @SerialName("logprobs")
    val logProbs: String?,
    @SerialName("finish_reason")
    val finishReason: String
)

@Serializable
data class XGroq(
    val id: String
) 