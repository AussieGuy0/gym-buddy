package dev.anthonybruno.gymbuddy.db

import org.slf4j.LoggerFactory
import java.lang.RuntimeException
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource


/**
 * A higher level interface that wraps JDBC and provides easier to use methods.
 */
interface JdbcHelper {

    fun <T> query(querySupplier: (Connection) -> PreparedStatement, resultMapper: (ResultSet) -> T): T

    fun <T> query(querySupplier: (Connection) -> PreparedStatement, rowMapper: (ResultSet, ResultContext) -> T): List<T>

    fun <T> queryOne(querySupplier: (Connection) -> PreparedStatement, rowMapper: (ResultSet, ResultContext) -> T): T?

    fun runTransaction(transactionHandler: (Connection) -> Unit)
}

interface ResultContext {

    val rowNum: Int
}


class DefaultJdbcHelper(private val dataSource: DataSource) : JdbcHelper {

    val log = LoggerFactory.getLogger(javaClass)

    override fun <T> query(querySupplier: (Connection) -> PreparedStatement, resultMapper: (ResultSet) -> T): T {
        dataSource.connection.use { conn ->
            querySupplier(conn).use { statement ->
                val queryStart = System.currentTimeMillis()
                statement.executeQuery().use {
                    log.info("Query executed (${System.currentTimeMillis() - queryStart}ms):\n$statement")
                    val result = resultMapper(it)
                    it.close()
                    return result
                }
            }
        }
    }

    override fun <T> query(querySupplier: (Connection) -> PreparedStatement, rowMapper: (ResultSet, ResultContext) -> T): List<T> {
        val ctx = DefaultResultContext(0)
        return query(querySupplier, { rs ->
            val result = mutableListOf<T>()
            while (rs.next()) {
                val mapped = rowMapper(rs, ctx.copy(rowNum = ctx.rowNum + 1))
                result.add(mapped)
            }
            result;
        })
    }


    override fun <T> queryOne(querySupplier: (Connection) -> PreparedStatement, rowMapper: (ResultSet, ResultContext) -> T): T? {
        val list = query(querySupplier, rowMapper)
        if (list.isEmpty()) {
            return null;
        } else if (list.size > 1) {
            // TODO: More information
            throw RuntimeException("Expected a single row but got ${list.size} rows instead!")
        }
        return list[0];
    }

    override fun runTransaction(transactionHandler: (Connection) -> Unit) {
        val conn = dataSource.connection
        conn.autoCommit = false;
        try {
            transactionHandler(conn)
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