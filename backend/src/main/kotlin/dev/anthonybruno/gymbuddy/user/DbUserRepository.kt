package dev.anthonybruno.gymbuddy.user

import dev.anthonybruno.gymbuddy.Server
import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher
import dev.anthonybruno.gymbuddy.auth.password.PasswordHasher
import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.util.getZoneId

import java.sql.SQLException
import java.time.ZoneId

class DbUserRepository(private val passwordHasher: PasswordHasher = BcryptPasswordHasher(), db: Database) : UserRepository {

    private val tableName = "users"
    private val dbHelper = db.getHelper()

    override fun addUser(email: String, password: String): UserDetails {
        return dbHelper.queryOne({ conn ->
            conn.prepareStatement("""
                    INSERT INTO $tableName(id, email, password, timezone) VALUES (DEFAULT, ?,?, DEFAULT)
                    RETURNING id, email, password, timezone
                """).apply {
                setString(1, email)
                setString(2, passwordHasher.hashPassword(password))
            }
        }, { rs, _ ->
            UserDetails(rs.getLong("id"), rs.getString("email"), rs.getZoneId("timezone"))
        })!! // Should never be null!
    }

    override fun getUserByEmail(email: String): InternalUserDetails? {
        return dbHelper.queryOne({
            val statement = it.prepareStatement("SELECT * FROM $tableName WHERE email=?")
            statement.setString(1, email)
            statement
        }, { rs, _ ->
            InternalUserDetails(rs.getInt("id").toLong(), rs.getString("password"), rs.getString("email"), rs.getZoneId("timezone"))
        })
    }
}
