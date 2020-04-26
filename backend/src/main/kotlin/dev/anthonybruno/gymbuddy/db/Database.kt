package dev.anthonybruno.gymbuddy.db

import org.postgresql.ds.PGSimpleDataSource
import java.io.PrintWriter
import java.net.URL
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.logging.Logger
import javax.sql.DataSource

class Database(private val username: String, private val password: String, private val url: String) {

    private val dataSource = createDataSource()

    fun getConnection(): Connection {
        return toDataSource().connection
    }

    fun getHelper(): JdbcHelper {
        return DefaultJdbcHelper(dataSource)
    }

    private fun toDataSource(): DataSource {
        return dataSource
    }

    private fun createDataSource(): DataSource {
        //TODO: Probably use Hikari
        val dataSource = PGSimpleDataSource()
        dataSource.setURL(url)
        dataSource.user = username
        dataSource.password = password
        dataSource.logWriter
        return dataSource
    }

}
