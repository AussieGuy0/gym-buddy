package dev.anthonybruno.gymbuddy.user

import dev.anthonybruno.gymbuddy.exception.BadRequestException

class UserService(private val userRepository: UserRepository) {

    // TODO: Support timezone
    fun addUser(email: String, password: String): UserDetails {
        if (password.length < 8) {
            throw BadRequestException("Password must be at least 8 characters long")
        }

        if (!email.contains("@")) {
            throw BadRequestException("Email ($email) not a email format")
        }
        return userRepository.addUser(email, password)
    }

    fun editUser(userId: Long, newDetails: UserDetails): UserDetails {
        throw UnsupportedOperationException()
    }

    fun getUser(userId: Long): UserDetails {
        throw UnsupportedOperationException()
    }

    fun deleteUser(userId: Long) {
        throw UnsupportedOperationException()
    }
}
