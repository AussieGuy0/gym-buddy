package au.com.anthonybruno.gymbuddy.db;

import java.sql.*;

public class Database {

    private final String username;
    private final String password;
    private final String url;

    public Database(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
