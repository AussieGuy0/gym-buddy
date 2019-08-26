package dev.anthonybruno.gymbuddy.user.model;

public class UserDetails {

    private final long id;
    private final String email;

    public static UserDetails NOOP = new UserDetails(0,  null);

    public UserDetails(long id,String email) {
        this.id = id;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
