package ekraft.javabrake.utils;


import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by ekraft on 8/29/15
 */
public class JavaBrakeUtils {

  public static String[] match(String line,
                               Pattern pattern,
                               String... defaultValues) {

    String[] result = match(line, pattern);
    if (result == null) {
      return defaultValues;
    }
    return result;
  }


  public static String[] match(String line,
                               Pattern pattern) {

    Matcher matcher = pattern.matcher(line);
    if (!matcher.find()) {
      return null;
    }

    String[] results = new String[matcher.groupCount()];
    for (int i = 0; i < results.length; i++) {
      results[i] = matcher.group(i + 1);
    }

    return results;
  }


  public static int durationToSeconds(String duration) {

    if (!duration.matches("\\d{2}:\\d{2}:\\d{2}")) {
      throw new IllegalArgumentException("Duration didn't match HH:MM:SS  -  " + duration);
    }

    String[] tokens = duration.split(":");

    int hours = Integer.parseInt(tokens[0]);
    int minutes = hours * 60 + Integer.parseInt(tokens[1]);
    return minutes * 60 + Integer.parseInt(tokens[2]);
  }


  public static <T> void verifyIncrementStartingWith(List<T> objects,
                                                     Function<T, Integer> getter,
                                                     String name,
                                                     int start) {

    int current = start;
    for (T object : objects) {
      if (getter.apply(object) != current++) {
        throw new IllegalArgumentException("Values were out of order for " + name);
      }
    }
  }
}
