package dev.anthonybruno.gymbuddy

object StartServer {

    @JvmStatic
    fun main(args: Array<String>) {
        val server = Server()
        server.start(8000)
    }
}
