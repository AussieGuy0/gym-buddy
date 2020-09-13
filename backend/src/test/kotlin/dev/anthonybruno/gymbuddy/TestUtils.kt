package dev.anthonybruno.gymbuddy

import java.util.*

object TestUtils {

    fun randomString(tag: String = ""): String {
        val separator = if (tag.isBlank()) "" else "-"
        return "$tag${separator}${UUID.randomUUID()}"
    }

    fun randomEmail(localPart: String = ""): String {
        return "${randomString(localPart)}@example.com"
    }
}