package dev.anthonybruno.gymbuddy.auth;


import dev.anthonybruno.gymbuddy.user.model.UserDetails;

import java.util.Optional;

public interface AuthenticationService {

    Optional<UserDetails> login(String email, String password);

}
