package dev.anthonybruno.gymbuddy.user

import dev.anthonybruno.gymbuddy.Server
import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher
import dev.anthonybruno.gymbuddy.auth.password.PasswordHasher
import dev.anthonybruno.gymbuddy.db.Database

import java.sql.SQLException

class DbUserRepository(private val passwordHasher: PasswordHasher, private val db: Database) : UserRepository {

    private val tableName = "users"
    private val dbHelper = db.getHelper()

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
        return dbHelper.queryOne({
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE email=?")
            statement.setString(1, email)
            statement
        }, { rs, _ ->
            InternalUserDetails(rs.getInt("id").toLong(), rs.getString("password"), rs.getString("email"))
        })
    }
}
