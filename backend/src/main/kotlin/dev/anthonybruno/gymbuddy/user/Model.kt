package dev.anthonybruno.gymbuddy.user

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.ZoneId

val noopUserDetails = UserDetails(0, null, null)

open class UserDetails(val id: Long, val email: String?, val timezone: ZoneId?)

class InternalUserDetails(id: Long, @field:JsonIgnore val password: String, email: String, timezone: ZoneId?) : UserDetails(id, email, timezone)

data class UserRego(val password: String, val email: String, val timezone: ZoneId?)
