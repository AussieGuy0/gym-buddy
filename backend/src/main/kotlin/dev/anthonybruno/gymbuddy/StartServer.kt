package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.util.ClassPathFile

object StartServer {

    private val CONFIG = if (System.getenv("STAGE") == "production") {
        EnvPropertiesConfig()
    } else {
        FileConfig(ClassPathFile("settings.properties").asPath())
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val server = Server()
        server.start(CONFIG.port)
    }
}
