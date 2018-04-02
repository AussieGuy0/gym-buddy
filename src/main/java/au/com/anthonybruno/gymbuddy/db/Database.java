package au.com.anthonybruno.gymbuddy.db;

import java.sql.*;
import java.util.function.Consumer;

public class Database {

    private final String username;
    private final String password;
    private final String url;

    public Database(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }


    public ResultSet executeQuery(String sql, Consumer<PreparedStatement> prepareStatement) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            prepareStatement.accept(statement);
            return statement.executeQuery();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
