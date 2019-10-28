package dev.anthonybruno.gymbuddy.user

import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher
import dev.anthonybruno.gymbuddy.common.Repository

import java.sql.SQLException

class UserRepository : Repository("users") {

    private val passwordHasher = BcryptPasswordHasher()

    fun addUser(email: String, password: String) {
        try {
            db.getConnection().use { conn ->
                conn.prepareStatement("INSERT INTO $tableName(email, password) VALUES (?,?)").use { statement ->
                    statement.setString(1, email)
                    statement.setString(2, passwordHasher.hashPassword(password))
                    statement.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }

    }

    fun getUserByEmail(email: String): InternalUserDetails? {
        try {
            db.getConnection().use { conn ->
                conn.prepareStatement("SELECT * FROM $tableName WHERE email=?").use { statement ->
                    statement.setString(1, email)
                    statement.executeQuery().use { results ->
                        return if (results.next()) {
                            return InternalUserDetails(results.getInt("id").toLong(), results.getString("password"), results.getString("email"))
                        } else null
                    }
                }
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }

    }
}
