package au.com.anthonybruno.gymbuddy.auth;


import java.util.Optional;

public interface AuthenticationService {

    Optional<UserDetails> login(String username, String password);

}
