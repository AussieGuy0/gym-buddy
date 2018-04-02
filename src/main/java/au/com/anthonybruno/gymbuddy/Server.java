package au.com.anthonybruno.gymbuddy;

import au.com.anthonybruno.gymbuddy.db.Database;
import au.com.anthonybruno.gymbuddy.util.ClassPathFile;
import io.javalin.Javalin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Server {

    public static final Properties SETTINGS = loadSettings("settings.properties");
    public static final Database DATABASE = new Database(SETTINGS.getProperty("db.username"), SETTINGS.getProperty("db.password"), SETTINGS.getProperty("db.url"));
    private final Javalin app = Javalin.create();

    public void start(int portNum) {
        app.port(portNum);
        app.enableStaticFiles("webapp");
        app.start();
        Urls urls = new Urls(app);
        urls.setupEndpoints();
    }

    public void stop() {
        app.stop();
    }

    private static Properties loadSettings(String location) {
        InputStream propertiesFile = new ClassPathFile(location).asStream();
        Properties properties = new Properties();
        try {
            properties.load(propertiesFile);
        } catch (IOException e) {
            throw new RuntimeException("Could not read settings files", e);
        }
        return properties;
    }
}
