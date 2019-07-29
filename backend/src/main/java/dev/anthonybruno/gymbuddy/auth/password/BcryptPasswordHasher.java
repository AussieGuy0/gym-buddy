package dev.anthonybruno.gymbuddy.auth.password;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptPasswordHasher implements PasswordHasher {

    @Override
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String hashed, String plain) {
        return BCrypt.checkpw(plain, hashed);
    }
}
