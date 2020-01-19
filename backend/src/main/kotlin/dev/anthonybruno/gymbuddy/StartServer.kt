package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.util.ClassPathFile

object StartServer {

    val CONFIG = if (System.getenv("STAGE") == "production") {
        EnvPropertiesConfig()
    } else {
        FileConfig.fromClassPath(ClassPathFile("settings.properties"))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val server = Server()
        server.start(CONFIG.port)
    }
}
