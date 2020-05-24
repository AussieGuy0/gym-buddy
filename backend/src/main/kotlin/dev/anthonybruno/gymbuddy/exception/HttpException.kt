package dev.anthonybruno.gymbuddy.exception

open class HttpException(val statusCode: Int, message: String) : RuntimeException(message) {

    fun asProblemDetails(): ProblemDetails {
        return ProblemDetails(message, statusCode)
    }
}
