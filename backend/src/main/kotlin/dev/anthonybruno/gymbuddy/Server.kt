package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.util.ClassPathFile
import io.javalin.Javalin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.sql.Connection
import java.sql.SQLException

class Server {
    private val app = Javalin.create { config -> config.addStaticFiles("webapp") }

    fun start(portNum: Int) {
        attemptDatabaseConnection()
        app.start(portNum)
        val routes = Routes(app)
        routes.setupEndpoints()
        app.after { context -> log.info(context.method() + " " + context.path() + " " + context.status()) }
    }

    fun stop() {
        app.stop()
    }

    private fun attemptDatabaseConnection() {
        try {
            DATABASE.getConnection().use { connection -> }
        } catch (e: SQLException) {
            throw IllegalStateException("Could not connect to database!", e)
        }

    }

    companion object {

        val CONFIG = Config(ClassPathFile("settings.properties").asPath())
        val DATABASE = Database(CONFIG.dbUsername, CONFIG.dbPassword, CONFIG.dbUrl)

        private val log = LoggerFactory.getLogger(Server::class.java)
    }

}
