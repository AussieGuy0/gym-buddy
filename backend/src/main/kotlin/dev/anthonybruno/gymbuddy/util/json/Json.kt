package dev.anthonybruno.gymbuddy.util.json

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode

import java.io.IOException

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

        private val objectMapper = ObjectMapper()

        fun <T> intoClass(json: String, c: Class<T>): T {
            try {
                return objectMapper.readValue(json, c)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }

        }
    }

}
