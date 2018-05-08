package au.com.anthonybruno.gymbuddy;

import au.com.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

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
