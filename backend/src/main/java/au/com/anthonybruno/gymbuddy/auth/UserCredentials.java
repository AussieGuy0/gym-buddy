package au.com.anthonybruno.gymbuddy.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCredentials {

    private final String username;
    private final String password;

    public UserCredentials(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
