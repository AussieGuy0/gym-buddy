package dev.anthonybruno.gymbuddy.auth

import dev.anthonybruno.gymbuddy.exception.BadRequestException
import dev.anthonybruno.gymbuddy.user.noopUserDetails
import dev.anthonybruno.gymbuddy.util.getSession
import io.javalin.http.Context


class AuthenticationController(private val authenticationService: AuthenticationService) {

    fun login(context: Context) {
        val (email, password) = context.bodyAsClass(UserCredentials::class.java)
        val userDetails = this.authenticationService.login(email, password)
                ?: throw BadRequestException("Incorrect details")

        SessionUtils.setSession(context, userDetails)
        context.status(200)
        context.json(userDetails)
    }

    fun logout(context: Context) {
        SessionUtils.setSession(context, noopUserDetails)
        context.status(200)
    }

    fun logCheck(context: Context) {
        val sessionDetails = context.getSession()
        if (sessionDetails == null || sessionDetails === noopUserDetails) {
            context.status(401)
        } else {
            context.json(sessionDetails)
        }
    }


}
