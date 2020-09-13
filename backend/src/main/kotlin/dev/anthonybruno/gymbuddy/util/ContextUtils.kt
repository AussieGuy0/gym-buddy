package dev.anthonybruno.gymbuddy.util

import dev.anthonybruno.gymbuddy.auth.SessionUtils
import dev.anthonybruno.gymbuddy.exception.BadRequestException
import dev.anthonybruno.gymbuddy.exception.HttpException
import dev.anthonybruno.gymbuddy.exception.UnauthorisedException
import dev.anthonybruno.gymbuddy.user.UserDetails
import dev.anthonybruno.gymbuddy.user.noopUserDetails
import io.javalin.http.Context
import java.lang.Long.parseLong

fun Context.getUserIdFromPath(): Long {
    val userId = pathParam("userId")
    if (userId.isEmpty()) {
        throw HttpException(400, "Need userId in path")
    }
    try {
        return parseLong(userId)
    } catch (e: NumberFormatException) {
        throw HttpException(400, "userId provided is not a number!")
    }
}

fun Context.ensureUserSignedIn() {
    val userDetails = getSession()
    if (userDetails == null || userDetails === noopUserDetails) {
        throw UnauthorisedException("Need to be logged in!")
    }
}

fun Context.getSession(): UserDetails? {
    return sessionAttribute<UserDetails>(SessionUtils.AUTH_ATTR_KEY)
}