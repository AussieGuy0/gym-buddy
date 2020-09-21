package dev.anthonybruno.gymbuddy

import com.fasterxml.jackson.databind.ObjectMapper
import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.util.json.objectMapper
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import java.net.URI
import java.sql.SQLException
import javax.print.attribute.IntegerSyntax


class Server(private val database: Database) {

    private val app = Javalin.create { config ->
        config.addStaticFiles("webapp")
        config.addSinglePageRoot("/", "webapp/index.html")
    }

    val port: Int
        get() = app.port()

    val url: URI
        get() = URI.create("http://localhost:$port")

    val objectMapper: ObjectMapper
        get() = JavalinJackson.getObjectMapper()

    fun start(portNum: Int) {
        attemptDatabaseConnection()
        runMigrations()
        app.start(portNum)
        val routes = Routes(app, database)
        routes.setupEndpoints()
        app.after { context -> log.info(context.method() + " " + context.path() + " " + context.status()) }
        JavalinJackson.configure(objectMapper)
    }

    fun stop() {
        app.stop()
    }

    private fun runMigrations() {
        val flyway = Flyway.configure()
                .dataSource(database.toDataSource()).load()
        flyway.migrate()
    }

    private fun attemptDatabaseConnection() {
        try {
            database.getConnection().use { connection -> }
        } catch (e: SQLException) {
            throw IllegalStateException("Could not connect to database!", e)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(Server::class.java)
    }


}
