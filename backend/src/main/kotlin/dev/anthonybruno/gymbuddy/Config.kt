package dev.anthonybruno.gymbuddy

import java.io.IOException
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

interface Config {

    val port: Int

    val dbUsername: String

    val dbPassword: String

    val dbUrl: String
}

class FileConfig(propertiesPath: Path): Config {

    private val properties: Properties
    override val port: Int
        get() = Integer.parseInt(properties.getProperty("port"))

    override val dbUsername: String
        get() = properties.getProperty("db.username")

    override val dbPassword: String
        get() = properties.getProperty("db.password")

    override val dbUrl: String
        get() = properties.getProperty("db.url")

    init {
        if (!Files.exists(propertiesPath)) {
            throw IllegalStateException("No properties file in path $propertiesPath")
        }
        properties = Properties()
        try {
            Files.newBufferedReader(propertiesPath).use { reader -> properties.load(reader) }
        } catch (e: IOException) {
            throw RuntimeException("Could not read settings files", e)
        }

    }
}

class EnvPropertiesConfig(): Config {

    override val port: Int
        get() = Integer.parseInt(System.getenv("PORT"))

    override val dbUsername: String
    override val dbPassword: String
    override val dbUrl: String


    init {
        val dbUri = URI(System.getenv("DATABASE_URL"))

        dbUsername = dbUri.userInfo.split(":")[0]
        dbPassword = dbUri.userInfo.split(":")[1]
        dbUrl = "jdbc:postgresql://" + dbUri.host + dbUri.path

    }
}
