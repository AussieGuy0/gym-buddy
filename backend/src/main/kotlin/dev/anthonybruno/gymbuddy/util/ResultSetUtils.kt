package dev.anthonybruno.gymbuddy.util

import java.sql.ResultSet
import java.time.ZoneId

fun ResultSet.getZoneId(column: String): ZoneId {
    return ZoneId.of(this.getString(column))
}