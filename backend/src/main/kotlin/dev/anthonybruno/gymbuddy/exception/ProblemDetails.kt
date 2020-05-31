package dev.anthonybruno.gymbuddy.exception

//TODO: https://tools.ietf.org/html/rfc7807
data class ProblemDetails(
        val message: String? = null,
        val statusCode: Int
)
