package dev.anthonybruno.gymbuddy.auth.password

import org.mindrot.jbcrypt.BCrypt

class BcryptPasswordHasher : PasswordHasher {

    override fun hashPassword(plainPassword: String): String {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt())
    }

    override fun checkPassword(hashed: String, plain: String): Boolean {
        return BCrypt.checkpw(plain, hashed)
    }
}
