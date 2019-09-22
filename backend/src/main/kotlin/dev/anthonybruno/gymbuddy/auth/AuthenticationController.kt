package dev.anthonybruno.gymbuddy.auth

import dev.anthonybruno.gymbuddy.exception.BadRequestException
import dev.anthonybruno.gymbuddy.user.UserDetails
import dev.anthonybruno.gymbuddy.util.json.Json
import io.javalin.http.Context


class AuthenticationController @JvmOverloads constructor(private val authenticationService: AuthenticationService = DbAuthenticationService()) {

    fun login(context: Context) {
        val (email, password) = context.body<UserCredentials>()
        val userDetails = this.authenticationService.login(email, password)
                ?: throw BadRequestException("Incorrect details")

        SessionUtils.setSession(context, userDetails)
        context.status(200)
        context.json(userDetails)
    }

    fun logout(context: Context) {
        SessionUtils.setSession(context, UserDetails.NOOP)
        context.status(200)
    }

    fun logCheck(context: Context) {
        val sessionDetails = SessionUtils.getSession(context)
        if (sessionDetails == null || sessionDetails === UserDetails.NOOP) {
            context.status(401)
        } else {
            context.json(sessionDetails)
        }
    }


}
