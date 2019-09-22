package dev.anthonybruno.gymbuddy

import java.io.IOException
import java.io.Reader
import java.nio.file.Files
import java.nio.file.Path
import java.util.Properties

class Config(propertiesPath: Path) {

    private val properties: Properties

    val dbUsername: String
        get() = properties.getProperty("db.username")

    val dbPassword: String
        get() = properties.getProperty("db.password")

    val dbUrl: String
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
