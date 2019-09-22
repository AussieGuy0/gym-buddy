package dev.anthonybruno.gymbuddy.auth

import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher
import dev.anthonybruno.gymbuddy.user.UserRepository
import dev.anthonybruno.gymbuddy.user.UserDetails

class DbAuthenticationService : AuthenticationService {

    private val userRepository = UserRepository()
    private val passwordHasher = BcryptPasswordHasher()

    override fun login(email: String, password: String): UserDetails? {
        val userDetails = userRepository.getUserByEmail(email) ?: return null

        val hashedPw = userDetails.password
        return if (passwordHasher.checkPassword(hashedPw, password)) {
            return userDetails
        } else null

    }
}
