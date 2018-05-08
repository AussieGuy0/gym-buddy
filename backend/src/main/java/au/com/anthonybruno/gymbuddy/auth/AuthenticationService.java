package au.com.anthonybruno.gymbuddy.auth;


import au.com.anthonybruno.gymbuddy.user.model.UserDetails;

import java.util.Optional;

public interface AuthenticationService {

    Optional<UserDetails> login(String username, String password);

}
