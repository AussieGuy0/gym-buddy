package dev.anthonybruno.gymbuddy.user

import dev.anthonybruno.gymbuddy.exception.BadRequestException
import dev.anthonybruno.gymbuddy.util.json.Json
import io.javalin.http.Context

class UserController constructor(private val userService: UserService = UserServiceImpl()) {

    fun addUser(context: Context) {
        val rego = Json.intoClass(context.body(), UserRego::class.java)
        val password = rego.password
        if (password.length < 8) {
            throw BadRequestException("Password must be at least 8 characters long")
        }

        val email = rego.email
        if (!email.contains("@")) {
            throw BadRequestException("Email ($email) not a email format")
        }
        userService.addUser(email, password)

    }

    fun editUser(context: Context) {
        throw UnsupportedOperationException()
    }

    fun deleteUser(context: Context) {
        val userId = getUserIdFromRequest(context)
        userService.deleteUser(userId)
    }

    fun getUser(context: Context) {
        val userId = getUserIdFromRequest(context)
        val userDetails = userService.getUser(userId)
        context.json(userDetails)
    }

    private fun getUserIdFromRequest(context: Context): Long {
        return java.lang.Long.parseLong(context.pathParam("userId"))
    }
}