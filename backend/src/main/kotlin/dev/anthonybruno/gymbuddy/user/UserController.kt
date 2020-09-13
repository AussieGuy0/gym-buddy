package dev.anthonybruno.gymbuddy.user

import dev.anthonybruno.gymbuddy.exception.BadRequestException
import dev.anthonybruno.gymbuddy.util.getUserIdFromPath
import dev.anthonybruno.gymbuddy.util.json.Json
import io.javalin.http.Context

class UserController(private val userService: UserService) {

    fun addUser(context: Context) {
        val rego = Json.intoClass(context.body(), UserRego::class.java)
        userService.addUser(rego.email, rego.password)
    }

    fun editUser(context: Context) {
        throw UnsupportedOperationException()
    }

    fun deleteUser(context: Context) {
        val userId = context.getUserIdFromPath()
        userService.deleteUser(userId)
    }

    fun getUser(context: Context) {
        val userId = context.getUserIdFromPath()
        val userDetails = userService.getUser(userId)
        context.json(userDetails)
    }

}