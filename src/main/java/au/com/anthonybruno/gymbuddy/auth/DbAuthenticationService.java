package au.com.anthonybruno.gymbuddy.auth;

import au.com.anthonybruno.gymbuddy.Server;
import au.com.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher;
import au.com.anthonybruno.gymbuddy.auth.password.PasswordHasher;
import au.com.anthonybruno.gymbuddy.db.Database;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class DbAuthenticationService implements AuthenticationService {

    private final Database db = Server.DATABASE;
    private final PasswordHasher passwordHasher = new BcryptPasswordHasher();

    @Override
    public Optional<UserDetails> login(String username, String password) {
        return null;
    }
}
