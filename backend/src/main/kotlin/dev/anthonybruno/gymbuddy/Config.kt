package dev.anthonybruno.gymbuddy;

import dev.anthonybruno.gymbuddy.db.Database;
import dev.anthonybruno.gymbuddy.util.ClassPathFile;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {

    private final Properties properties;

    public Config(Path propertiesPath) {
        if (!Files.exists(propertiesPath)) {
            throw new IllegalStateException("No properties file in path " + propertiesPath);
        }
        properties = new Properties();
        try (Reader reader = Files.newBufferedReader(propertiesPath)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Could not read settings files", e);
        }
    }

    public String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public String getDbPassword() {
        return properties.getProperty("db.password");
    }

    public String getDbUrl() {
        return properties.getProperty("db.url");
    }


}
