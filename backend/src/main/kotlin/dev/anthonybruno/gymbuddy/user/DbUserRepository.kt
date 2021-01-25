package dev.anthonybruno.gymbuddy.user

import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher
import dev.anthonybruno.gymbuddy.auth.password.PasswordHasher
import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.db.jooq.tables.references.USERS

import java.time.ZoneId

class DbUserRepository(private val passwordHasher: PasswordHasher = BcryptPasswordHasher(), private val db: Database) : UserRepository {

    private val defaultTimezone = "Australia/Adelaide";

    override fun addUser(email: String, password: String): UserDetails {
        val user = db.jooq().newRecord(USERS).apply {
            this.email = email
            this.password = passwordHasher.hashPassword(password)
            this.timezone = defaultTimezone
            store()
        }
        return UserDetails(user.id!!, user.email, ZoneId.of(user.timezone))
    }

    override fun getUserByEmail(email: String): InternalUserDetails? {
        val user = db.jooq().fetchOne(USERS, USERS.EMAIL.eq(email)) ?: return null
        return InternalUserDetails(user.id!!, user.password!!, user.email!!, ZoneId.of(user.timezone))
    }
}
