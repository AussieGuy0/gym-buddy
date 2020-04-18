package dev.anthonybruno.gymbuddy.user

import dev.anthonybruno.gymbuddy.Server
import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher
import dev.anthonybruno.gymbuddy.auth.password.PasswordHasher

import java.sql.SQLException

class DbUserRepository(private val passwordHasher: PasswordHasher): UserRepository {

    private val tableName = "users"
    private val db = Server.DATABASE

    override fun addUser(email: String, password: String) {
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

    override fun getUserByEmail(email: String): InternalUserDetails? {
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
