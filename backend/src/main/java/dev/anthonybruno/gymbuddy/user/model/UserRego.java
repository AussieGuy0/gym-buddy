package dev.anthonybruno.gymbuddy.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRego {

    private final String password;
    private final String email;

    public UserRego( @JsonProperty("password") String password, @JsonProperty("email") String email) {
        this.password = password;
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
