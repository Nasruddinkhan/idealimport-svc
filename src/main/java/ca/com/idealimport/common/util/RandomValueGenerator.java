package ca.com.idealimport.common.util;

import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class RandomValueGenerator {

    public static String generateRandomString(int length, String characters) {
        var random = new Random();
        return random.ints(length, 0, characters.length())
                .mapToObj(i -> String.valueOf(characters.charAt(i)))
                .collect(Collectors.joining());
    }
}
