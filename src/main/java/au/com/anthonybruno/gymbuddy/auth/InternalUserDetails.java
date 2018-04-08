package au.com.anthonybruno.gymbuddy.auth;

public class InternalUserDetails extends UserDetails {

    private final String password;

    public InternalUserDetails(long id, String username, String password, String email) {
        super(id, username, email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
