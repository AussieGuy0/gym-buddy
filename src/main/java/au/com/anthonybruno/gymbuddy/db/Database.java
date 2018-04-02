package au.com.anthonybruno.gymbuddy.db;

import au.com.anthonybruno.gymbuddy.util.FileUtils;

import javax.naming.InitialContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Database {

    private final String username;
    private final String password;
    private final String url;

    public Database(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
