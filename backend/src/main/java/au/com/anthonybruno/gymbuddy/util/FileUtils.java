package au.com.anthonybruno.gymbuddy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static List<String> getLines(File file) throws FileNotFoundException {
        return getLinesStream(file).collect(Collectors.toList());
    }

    public static Stream<String> getLinesStream(File file) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return reader.lines();
    }
}
