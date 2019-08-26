package dev.anthonybruno.gymbuddy.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class InternalUserDetails extends UserDetails {

    @JsonIgnore
    private final String password;

    public InternalUserDetails(long id, String password, String email) {
        super(id, email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
