package dev.anthonybruno.gymbuddy;

import dev.anthonybruno.gymbuddy.db.Database;
import dev.anthonybruno.gymbuddy.util.ClassPathFile;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Server {

    public static final Config CONFIG = new Config(new ClassPathFile("settings.properties").asPath());
    public static final Database DATABASE = new Database(CONFIG.getDbUsername(), CONFIG.getDbPassword(), CONFIG.getDbUrl());

    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private final Javalin app = Javalin.create(config -> config.addStaticFiles("webapp"));

    public void start(int portNum) {
        attemptDatabaseConnection();
        app.start(portNum);
        Routes routes = new Routes(app);
        routes.setupEndpoints();
        app.after((context) -> {
           log.info(context.method() + " " +  context.path() + " " + context.status());
        });
    }

    public void stop() {
        app.stop();
    }

    private void attemptDatabaseConnection() {
        try (Connection connection = DATABASE.getConnection()) {
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect to database!", e);
        }
    }

}
