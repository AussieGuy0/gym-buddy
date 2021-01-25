package dev.anthonybruno.gymbuddy.util

import dev.anthonybruno.gymbuddy.auth.SessionUtils
import dev.anthonybruno.gymbuddy.exception.HttpException
import dev.anthonybruno.gymbuddy.exception.UnauthorisedException
import dev.anthonybruno.gymbuddy.user.UserDetails
import dev.anthonybruno.gymbuddy.user.noopUserDetails
import io.javalin.http.Context
import java.lang.Integer.parseInt
import java.lang.Long.parseLong

fun Context.getUserIdFromPath(): Int {
    val userId = pathParam("userId")
    if (userId.isEmpty()) {
        throw HttpException(400, "Need userId in path")
    }
    try {
        return parseInt(userId)
    } catch (e: NumberFormatException) {
        throw HttpException(400, "userId provided is not a number!")
    }
}

fun Context.verifyUserAndGetIdFromPath(): Int {
    ensureUserSignedIn()
    val userId = getUserIdFromPath()
    val userDetails = getSession() ?: throw UnauthorisedException("Need to be logged in!")

    if (userId != userDetails.id) {
        throw UnauthorisedException("Can't access someone else's information!")
    }
    return userId
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