package dev.anthonybruno.gymbuddy.user

class UserServiceImpl : UserService {

    private var userRepository = UserRepository()

    override fun addUser(email: String, password: String): UserDetails {
        userRepository.addUser(email, password)
        return UserDetails(0, email)
    }

    override fun editUser(userId: Long, newDetails: UserDetails): UserDetails {
        throw UnsupportedOperationException()
    }

    override fun getUser(userId: Long): UserDetails {
        throw UnsupportedOperationException()
    }

    override fun deleteUser(userId: Long) {
        throw UnsupportedOperationException()
    }
}
