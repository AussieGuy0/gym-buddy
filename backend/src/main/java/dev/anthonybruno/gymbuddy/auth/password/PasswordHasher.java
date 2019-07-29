package dev.anthonybruno.gymbuddy.auth.password;

public interface PasswordHasher {

    String hashPassword(String plainPassword);

    boolean checkPassword(String hashed, String plain);
}
