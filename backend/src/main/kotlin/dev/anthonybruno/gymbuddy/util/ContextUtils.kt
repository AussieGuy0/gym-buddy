package dev.anthonybruno.gymbuddy.util

import dev.anthonybruno.gymbuddy.auth.SessionUtils
import dev.anthonybruno.gymbuddy.exception.BadRequestException
import dev.anthonybruno.gymbuddy.exception.HttpException
import dev.anthonybruno.gymbuddy.exception.UnauthorisedException
import dev.anthonybruno.gymbuddy.user.noopUserDetails
import io.javalin.http.Context
import java.lang.Long.parseLong

fun getUserIdFromPath(context: Context): Long {
    val userId = context.pathParam("userId")
    if (userId.isEmpty()) {
        throw HttpException(400, "Need userId in path")
    }
    try {
        return parseLong(userId)
    } catch (e: NumberFormatException) {
        throw HttpException(400, "userId provided is not a number!")
    }
}

fun ensureUserSignedIn(context: Context) {
    val userDetails = SessionUtils.getSession(context)
    if (userDetails == null || userDetails === noopUserDetails) {
        throw UnauthorisedException("Need to be logged in!")
    }
}
