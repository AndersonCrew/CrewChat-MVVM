package com.crewcloud.apps.crewchat.data.dto.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

/**
 * Created by BM Anderson on 3/7/26.
 */
@Serializable
data class BaseResponse<T>(
    @SerialName("d") val d: Data<T>
)

@Serializable
data class Data<out T>(
    @SerialName("data") val data: T,
    @Serializable(with = SuccessSerializer::class)
    @SerialName("success") val success: Boolean,
    @SerialName("error") val error: BaseError?= null
)

@Serializable
data class BaseError(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String
)

object SuccessSerializer : KSerializer<Boolean> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "Success",
        kind = PrimitiveKind.BOOLEAN
    )

    override fun deserialize(decoder: Decoder): Boolean {
        val jsonDecoder = decoder as? JsonDecoder ?: return decoder.decodeBoolean()
        val primitive = jsonDecoder.decodeJsonElement().jsonPrimitive
        primitive.booleanOrNull?.let { return it }
        primitive.intOrNull?.let { return it != 0 }
        return when (primitive.contentOrNull?.lowercase()) {
            "true", "1" -> true
            else -> false
        }
    }

    override fun serialize(encoder: Encoder, value: Boolean) {
        encoder.encodeBoolean(value)
    }
}
