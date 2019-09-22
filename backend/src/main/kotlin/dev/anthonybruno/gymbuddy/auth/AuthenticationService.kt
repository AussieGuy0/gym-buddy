package dev.anthonybruno.gymbuddy.auth


import dev.anthonybruno.gymbuddy.user.UserDetails

interface AuthenticationService {

    fun login(email: String, password: String): UserDetails?

}
