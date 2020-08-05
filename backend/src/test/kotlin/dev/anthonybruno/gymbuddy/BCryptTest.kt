package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class BCryptTest {

    @Test
    fun hashedPasswordDifferentToOriginal() {
        val hasher = BcryptPasswordHasher()
        val plainPassword = "fmewfoiwe"
        val hashedPassword = hasher.hashPassword(plainPassword)
        assertThat(plainPassword).isNotEqualTo(hashedPassword)
    }

    @Test
    fun hashAndCheck() {
        val hasher = BcryptPasswordHasher()
        val plainPassword = "fmewfoiwe"
        val hashedPassword = hasher.hashPassword(plainPassword)
        assertThat(hasher.checkPassword(hashedPassword, plainPassword)).isTrue()
    }

    @Test
    fun ensureCheckFailsOnWrongPassword() {
        val hasher = BcryptPasswordHasher()
        val plainPassword = "thisisapassword"
        val hashedPassword = hasher.hashPassword(plainPassword)
        assertThat(hasher.checkPassword(hashedPassword, "notcorrect")).isFalse()
    }

}