package dev.anthonybruno.gymbuddy.auth

import dev.anthonybruno.gymbuddy.auth.password.PasswordHasher
import dev.anthonybruno.gymbuddy.user.DbUserRepository
import dev.anthonybruno.gymbuddy.user.UserDetails
import dev.anthonybruno.gymbuddy.user.UserRepository

class AuthenticationService(private val userRepository: UserRepository, private val passwordHasher: PasswordHasher) {

    fun login(email: String, password: String): UserDetails? {
        val userDetails = userRepository.getUserByEmail(email) ?: return null

        val hashedPw = userDetails.password
        return if (passwordHasher.checkPassword(hashedPw, password)) {
            return userDetails
        } else null
    }

}
