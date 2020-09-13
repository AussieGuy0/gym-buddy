package dev.anthonybruno.gymbuddy.auth

import dev.anthonybruno.gymbuddy.auth.SessionUtils.AUTH_ATTR_KEY
import dev.anthonybruno.gymbuddy.user.UserDetails
import io.javalin.http.Context

object SessionUtils {

    const val AUTH_ATTR_KEY = "auth"

    fun setSession(context: Context, userDetails: UserDetails) {
        context.sessionAttribute(AUTH_ATTR_KEY, userDetails)
    }

}
