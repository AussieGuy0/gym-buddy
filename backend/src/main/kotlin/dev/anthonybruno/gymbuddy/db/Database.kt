package dev.anthonybruno.gymbuddy.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Database(private val username: String, private val password: String, private val url: String) {

    fun getConnection(): Connection {
        return DriverManager.getConnection(url, username, password)
    }

}