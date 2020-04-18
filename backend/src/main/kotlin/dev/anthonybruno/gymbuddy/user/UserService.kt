package dev.anthonybruno.gymbuddy.user

class UserService(private val userRepository: UserRepository) {

    fun addUser(email: String, password: String): UserDetails {
        userRepository.addUser(email, password)
        return UserDetails(0, email)
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
