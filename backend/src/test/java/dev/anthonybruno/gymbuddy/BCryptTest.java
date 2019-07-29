package dev.anthonybruno.gymbuddy;

import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BCryptTest {

    @Test
    public void hashAndCheck() {
        BcryptPasswordHasher hasher = new BcryptPasswordHasher();
        String plainPassword =  "fmewfoiwe";
        String hashedPassword = hasher.hashPassword(plainPassword);
        assertTrue(hasher.checkPassword(hashedPassword, plainPassword));
    }

    @Test
    public void ensureCheckFailsOnWrongPassword() {
        BcryptPasswordHasher hasher = new BcryptPasswordHasher();
        String plainPassword =  "thisisapassword";
        String hashedPassword = hasher.hashPassword(plainPassword);
        assertFalse(hasher.checkPassword(hashedPassword, "notthecorrectpassword"));
    }
}
