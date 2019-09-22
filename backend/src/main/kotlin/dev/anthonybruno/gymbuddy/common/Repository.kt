package dev.anthonybruno.gymbuddy.common

import dev.anthonybruno.gymbuddy.Server

abstract class Repository(protected val tableName: String) {
        protected val db = Server.DATABASE
}