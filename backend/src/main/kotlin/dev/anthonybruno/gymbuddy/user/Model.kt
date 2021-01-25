package dev.anthonybruno.gymbuddy.user

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.ZoneId

val noopUserDetails = UserDetails(0, null, null)

open class UserDetails(val id: Int, val email: String?, val timezone: ZoneId?)

class InternalUserDetails(id: Int, @field:JsonIgnore val password: String, email: String, timezone: ZoneId?) : UserDetails(id, email, timezone)

data class UserRego(val password: String, val email: String, val timezone: ZoneId?)
