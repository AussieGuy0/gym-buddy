package au.com.anthonybruno.gymbuddy.auth;

import au.com.anthonybruno.gymbuddy.user.model.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryAuthenticationService implements AuthenticationService {

    private static final Map<String, UserDetails> userMap = new HashMap<>();
    static {
        userMap.put("user", new UserDetails(1, "user", "abc"));
    }

    @Override
    public Optional<UserDetails> login(String username, String password) {
        return Optional.ofNullable(userMap.get(username));
    }

}
