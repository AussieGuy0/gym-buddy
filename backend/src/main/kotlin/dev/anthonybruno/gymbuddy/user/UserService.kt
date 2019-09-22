package dev.anthonybruno.gymbuddy.user

interface UserService {

    fun addUser(email: String, password: String): UserDetails

    fun editUser(userId: Long, newDetails: UserDetails): UserDetails

    fun getUser(userId: Long): UserDetails

    fun deleteUser(userId: Long)

}
