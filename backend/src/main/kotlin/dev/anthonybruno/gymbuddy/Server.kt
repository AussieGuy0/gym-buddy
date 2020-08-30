package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.util.json.objectMapper
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import java.sql.SQLException


class Server(private val database: Database) {

    companion object {

        private val log = LoggerFactory.getLogger(Server::class.java)

    }

    private val app = Javalin.create { config ->
        config.addStaticFiles("webapp")
        config.addSinglePageRoot("/", "webapp/index.html")
    }


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


}
