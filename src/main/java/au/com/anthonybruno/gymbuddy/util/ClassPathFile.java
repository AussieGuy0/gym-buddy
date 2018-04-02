package au.com.anthonybruno.gymbuddy.util;

import java.io.InputStream;
import java.net.URL;

public class ClassPathFile {

    private final String location;

    public ClassPathFile(String location) {
        this.location = location;
    }

    public InputStream asStream() {
        return getClassLoader().getResourceAsStream(location);
    }

    public URL asUrl() {
        return getClassLoader().getResource(location);
    }

    private ClassLoader getClassLoader() {
        return this.getClass().getClassLoader();
    }


}
