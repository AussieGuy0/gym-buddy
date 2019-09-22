package dev.anthonybruno.gymbuddy.util;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class ClassPathFile {

    private final Class<?> context;
    private final String location;

    public ClassPathFile(String location) {
        this(location, ClassPathFile.class);
    }

    public ClassPathFile(String location, Class<?> context) {
        this.location = location;
        this.context = context;
    }

    public boolean exists() {
        return asStream() != null;
    }

    public InputStream asStream() {
        return getClassLoader().getResourceAsStream(location);
    }

    public URL asUrl() {
        return getClassLoader().getResource(location);
    }

    public Path asPath() {
        try {
            return Path.of(asUrl().toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private ClassLoader getClassLoader() {
        return context.getClassLoader();
    }


}
