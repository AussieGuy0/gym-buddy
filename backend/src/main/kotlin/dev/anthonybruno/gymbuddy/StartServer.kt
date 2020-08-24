package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.db.Database
import dev.anthonybruno.gymbuddy.util.ClassPathFile

fun loadConfig(): Config {
    return if (System.getenv("STAGE") == "production") {
        EnvPropertiesConfig()
    } else {
        FileConfig.fromClassPath(ClassPathFile("settings.properties"))
    }
}

object StartServer {

    private val CONFIG = loadConfig()

    @JvmStatic
    fun main(args: Array<String>) {
        val server = Server(Database(CONFIG.dbUsername, CONFIG.dbPassword, CONFIG.dbUrl))
        server.start(CONFIG.port)
    }
}
