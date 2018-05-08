package au.com.anthonybruno.gymbuddy.util;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

    public static void closeNoException(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception ignored) {
        }
    }
}
