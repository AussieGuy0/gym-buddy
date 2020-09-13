package dev.anthonybruno.gymbuddy.user

interface UserRepository {

    fun addUser(email: String, password: String): UserDetails

    fun getUserByEmail(email: String): InternalUserDetails?

}