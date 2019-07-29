package dev.anthonybruno.gymbuddy;

import dev.anthonybruno.gymbuddy.db.Database;
import dev.anthonybruno.gymbuddy.util.ClassPathFile;
import io.javalin.Javalin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Server {

    public static final Properties SETTINGS = loadSettings("settings.properties");
    public static final Database DATABASE = new Database(SETTINGS.getProperty("db.username"), SETTINGS.getProperty("db.password"), SETTINGS.getProperty("db.url"));
    private final Javalin app = Javalin.create(config -> config.addStaticFiles("webapp"));

    public void start(int portNum) {
        app.start(portNum);
        Urls urls = new Urls(app);
        urls.setupEndpoints();
    }

    public void stop() {
        app.stop();
    }

    private static Properties loadSettings(String location) {
        ClassPathFile propertiesFile = new ClassPathFile(location);
        if (!propertiesFile.exists()) {
            throw new IllegalStateException("No properties file in path " + location);
        }
        Properties properties = new Properties();
        try {
            properties.load(propertiesFile.asStream());
        } catch (IOException e) {
            throw new RuntimeException("Could not read settings files", e);
        }
        return properties;
    }
}
