package dev.anthonybruno.gymbuddy.db

import java.lang.RuntimeException
import java.sql.Connection
import java.sql.DriverManager.getConnection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource


interface JdbcHelper {

    fun <T> query(creator: (Connection) -> PreparedStatement, rowMapper: (ResultSet, ResultContext) -> T): List<T>

    fun <T> queryOne(creator: (Connection) -> PreparedStatement, rowMapper: (ResultSet, ResultContext) -> T): T?

    fun runTransaction(transactionConnectionConsumer: (Connection) -> Unit)
}

interface ResultContext {

    val rowNum: Int
}


class DefaultJdbcHelper(private val dataSource: DataSource) : JdbcHelper {

    override fun <T> query(creator: (Connection) -> PreparedStatement, rowMapper: (ResultSet, ResultContext) -> T): List<T> {
        val ctx = DefaultResultContext(0)
        dataSource.connection.use { conn ->
            creator(conn).use { statement ->
                statement.executeQuery().use {
                    val result = mutableListOf<T>()
                    while (it.next()) {
                        val mapped = rowMapper(it, ctx.copy(rowNum = ctx.rowNum + 1))
                        result.add(mapped)
                    }
                    return result;
                }
            }
        }
    }

    override fun <T> queryOne(creator: (Connection) -> PreparedStatement, rowMapper: (ResultSet, ResultContext) -> T): T? {
        val list = query(creator, rowMapper)
        if (list.isEmpty()) {
            return null;
        } else if (list.size > 1) {
            // TODO: More information
            throw RuntimeException("Expected a single row but got ${list.size} rows instead!")
        }
        return list[0];
    }

    override fun runTransaction(transactionConnectionConsumer: (Connection) -> Unit) {
        val conn = dataSource.connection
        conn.autoCommit = false;
        try {
            transactionConnectionConsumer(conn)
            conn.commit()
        } catch (e: SQLException) {
            conn.rollback()
            throw e
        } finally {
            conn.autoCommit = false
            conn.close()
        }
    }
}

data class DefaultResultContext(override val rowNum: Int) : ResultContext