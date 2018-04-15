package au.com.anthonybruno.gymbuddy.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetails {

    private final long id;
    private final String username;
    private final String email;

    public static UserDetails NOOP = new UserDetails(0, null, null);

    public UserDetails(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
