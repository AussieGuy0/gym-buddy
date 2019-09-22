package dev.anthonybruno.gymbuddy.user;

import dev.anthonybruno.gymbuddy.user.model.InternalUserDetails;
import dev.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher;
import dev.anthonybruno.gymbuddy.auth.password.PasswordHasher;
import dev.anthonybruno.gymbuddy.common.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository extends Repository {

    private PasswordHasher passwordHasher = new BcryptPasswordHasher();

    public UserRepository() {
        super("users");
    }

    public void addUser(String email, String password) {
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO " + tableName + "(email, password) VALUES (?,?,?)")) {
            statement.setString(1, email);
            statement.setString(2, passwordHasher.hashPassword(password));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<InternalUserDetails> getUserByEmail(String email) {
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE email=?")) {
            statement.setString(1, email);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    return Optional.of(new InternalUserDetails(results.getInt("id"), results.getString("password"), results.getString("email")));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
