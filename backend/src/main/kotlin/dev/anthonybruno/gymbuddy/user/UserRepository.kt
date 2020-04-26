package dev.anthonybruno.gymbuddy.user

interface UserRepository {

    fun addUser(email: String, password: String)

    fun getUserByEmail(email: String): InternalUserDetails?

}