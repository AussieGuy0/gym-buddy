package dev.anthonybruno.gymbuddy.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRego {

    private final String username;
    private final String password;
    private final String email;

    public UserRego(@JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("email") String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
