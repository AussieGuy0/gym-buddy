package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.util.json.JavalinJsonMapper
import dev.anthonybruno.gymbuddy.util.json.createObjectMapper
import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import java.net.URI
import java.sql.SQLException


class Server(private val database: Database) {

    val objectMapper = createObjectMapper();

    private val app = Javalin.create { config ->
        config.addStaticFiles("webapp", Location.CLASSPATH)
        config.addSinglePageRoot("/", "webapp/index.html")
        config.jsonMapper(JavalinJsonMapper(objectMapper));
    }

    val port: Int
        get() = app.port()

    val url: URI
        get() = URI.create("http://localhost:$port")


    fun start(portNum: Int) {
        attemptDatabaseConnection()
        runMigrations()
        app.start(portNum)
        val routes = Routes(app, database)
        routes.setupEndpoints()
        app.after { context -> log.info(context.method() + " " + context.path() + " " + context.status()) }
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
