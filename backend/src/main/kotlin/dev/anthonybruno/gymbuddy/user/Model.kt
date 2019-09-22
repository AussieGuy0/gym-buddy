package dev.anthonybruno.gymbuddy.user

import com.fasterxml.jackson.annotation.JsonIgnore

open class UserDetails(val id: Long, val email: String?) {
    companion object {

        var NOOP = UserDetails(0, null)
    }
}


class InternalUserDetails(id: Long, @field:JsonIgnore val password: String, email: String) : UserDetails(id, email)


data class UserRego(val password: String, val email: String)
