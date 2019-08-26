package dev.anthonybruno.gymbuddy.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCredentials {

    private final String email;
    private final String password;

    public UserCredentials(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
