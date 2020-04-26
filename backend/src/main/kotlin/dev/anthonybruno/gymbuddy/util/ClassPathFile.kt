package dev.anthonybruno.gymbuddy.util

import java.io.InputStream
import java.net.URISyntaxException
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths

class ClassPathFile(private val location: String, private val context: Class<*> = ClassPathFile::class.java) {

    private val classLoader: ClassLoader
        get() = context.classLoader

    fun exists(): Boolean {
        return asStream() != null
    }

    fun asStream(): InputStream? {
        return classLoader.getResourceAsStream(location)
    }

    fun asUrl(): URL? {
        return classLoader.getResource(location)
    }

    fun asPath(): Path {
        try {
            return Paths.get(asUrl()!!.toURI())
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }

    }


}
