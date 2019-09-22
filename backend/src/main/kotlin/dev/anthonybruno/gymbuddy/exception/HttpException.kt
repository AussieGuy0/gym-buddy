package dev.anthonybruno.gymbuddy.exception

open class HttpException(val statusCode: Int, message: String) : RuntimeException(message) {

    fun serialise(): Map<String, Any?> {
        return mapOf("message" to message, "statusCode" to statusCode)
    }
}
