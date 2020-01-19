package dev.anthonybruno.gymbuddy

import dev.anthonybruno.gymbuddy.util.ClassPathFile
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.net.URI
import java.util.*

interface Config {

    val port: Int

    val dbUsername: String

    val dbPassword: String

    val dbUrl: String
}

class FileConfig(private val properties: Properties): Config {

    override val port: Int
        get() = Integer.parseInt(properties.getProperty("port"))

    override val dbUsername: String
        get() = properties.getProperty("db.username")

    override val dbPassword: String
        get() = properties.getProperty("db.password")

    override val dbUrl: String
        get() = properties.getProperty("db.url")

    companion object {
        fun fromReader(propertiesReader: Reader): FileConfig {
            val props = Properties()
            propertiesReader.use { reader -> props.load(reader) }
            return FileConfig(props)
        }

        fun fromClassPath(classPathFile: ClassPathFile): FileConfig {
            if (!classPathFile.exists()) {
                throw IllegalStateException("No properties file in path $classPathFile")
            }
            return fromReader(InputStreamReader(classPathFile.asStream()!!))
        }
    }
    init {
        try {
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
