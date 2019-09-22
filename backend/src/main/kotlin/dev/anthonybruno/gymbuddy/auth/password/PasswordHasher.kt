package dev.anthonybruno.gymbuddy.auth.password

interface PasswordHasher {

    fun hashPassword(plainPassword: String): String

    fun checkPassword(hashed: String, plain: String): Boolean
}