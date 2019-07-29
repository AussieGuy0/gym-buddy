package dev.anthonybruno.gymbuddy.auth;

import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher;
import dev.anthonybruno.gymbuddy.auth.password.PasswordHasher;
import dev.anthonybruno.gymbuddy.user.UserRepository;
import dev.anthonybruno.gymbuddy.user.model.InternalUserDetails;
import dev.anthonybruno.gymbuddy.user.model.UserDetails;

import java.util.Optional;

public class DbAuthenticationService implements AuthenticationService {

    private final UserRepository userRepository = new UserRepository();
    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    @Override
    public Optional<UserDetails> login(String username, String password) {
        Optional<InternalUserDetails> userDetails = userRepository.getUserByUsername(username);
        if (userDetails.isPresent()) {
           String hashedPw = userDetails.get().getPassword();
           if (passwordHasher.checkPassword(hashedPw, password)) {
               return Optional.of(userDetails.get());
           }
        }
        return Optional.empty();
    }
}
