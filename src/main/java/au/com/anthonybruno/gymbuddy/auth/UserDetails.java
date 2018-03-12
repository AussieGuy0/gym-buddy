package au.com.anthonybruno.gymbuddy.auth;

public class UserDetails {

    private final String username;
    private final String email;

    public UserDetails(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
