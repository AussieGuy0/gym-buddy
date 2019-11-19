package dev.anthonybruno.gymbuddy.util.json

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

import java.io.IOException

val objectMapper = createObjectMapper()

fun createObjectMapper(): ObjectMapper {
    return ObjectMapper()
            .registerModule(KotlinModule())
            .registerModule(Jdk8Module())
            .registerModule(JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
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
