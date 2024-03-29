package dev.anthonybruno.gymbuddy.util.json

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.javalin.json.JsonMapper

import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type

val objectMapper = createObjectMapper()

fun createObjectMapper(): ObjectMapper {
    return ObjectMapper()
            .registerModule(KotlinModule.Builder().build())
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
}

class JavalinJsonMapper(private val objectMapper: ObjectMapper): JsonMapper {

    override fun toJsonString(obj: Any, type: Type): String {
        return objectMapper.writeValueAsString(obj)
    }

    override fun toJsonStream(obj: Any, type: Type): InputStream {
        // TODO: Implement.
        return super.toJsonStream(obj, type)
    }

    override fun <T : Any> fromJsonString(json: String, targetType: Type): T {
        return objectMapper.readValue(json, objectMapper.typeFactory.constructType(targetType))
    }

    override fun <T : Any> fromJsonStream(json: InputStream, targetType: Type): T {
        return objectMapper.readValue(json, objectMapper.typeFactory.constructType(targetType))
    }
}

class Json(json: String) {

    private val jsonNode: JsonNode

    init {
        try {
            jsonNode = objectMapper.readTree(json)
        } catch (e: IOException) {
            throw IllegalStateException(e)
        }

    }

    fun asObject(): ObjectNode {
        return jsonNode as ObjectNode
    }

    fun asArray(): ArrayNode {
        return jsonNode as ArrayNode
    }

    companion object {

        fun <T> intoClass(json: String, c: Class<T>): T {
            try {
                return objectMapper.readValue(json, c)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }

        }
    }

}
