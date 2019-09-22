package dev.anthonybruno.gymbuddy.auth

import dev.anthonybruno.gymbuddy.user.UserDetails
import io.javalin.http.Context

object SessionUtils {

    var AUTH_ATTR_KEY = "auth"

    fun setSession(context: Context, userDetails: UserDetails) {
        context.sessionAttribute(AUTH_ATTR_KEY, userDetails)
    }

    fun getSession(context: Context): UserDetails? {
        return context.sessionAttribute<UserDetails>(AUTH_ATTR_KEY)
    }
}
