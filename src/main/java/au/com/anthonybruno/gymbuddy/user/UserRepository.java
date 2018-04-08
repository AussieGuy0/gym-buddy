package au.com.anthonybruno.gymbuddy.user;

import au.com.anthonybruno.gymbuddy.auth.InternalUserDetails;
import au.com.anthonybruno.gymbuddy.auth.UserCredentials;
import au.com.anthonybruno.gymbuddy.auth.password.BcryptPasswordHasher;
import au.com.anthonybruno.gymbuddy.auth.password.PasswordHasher;
import au.com.anthonybruno.gymbuddy.common.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository extends Repository {

    PasswordHasher passwordHasher = new BcryptPasswordHasher();

    public UserRepository() {
        super("users");
    }

    public void addUser(String username, String password, String email) {
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO " + tableName + " VALUES (?,?,?)")) {
            statement.setString(1, username);
            statement.setString(2, passwordHasher.hashPassword(password));
            statement.setString(3, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<InternalUserDetails> getUserByUsername(String username) {
        try (Connection conn = db.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE username=?")) {
            statement.setString(1, username);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    return Optional.of(new InternalUserDetails(results.getInt("id"), results.getString("username"), results.getString("password"), results.getString("email")));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
