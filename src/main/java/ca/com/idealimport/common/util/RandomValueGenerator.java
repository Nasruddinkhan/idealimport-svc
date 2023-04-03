package ca.com.idealimport.common.util;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomValueGenerator {
    private RandomValueGenerator()  {}
    public static String generateRandomString(int length, String characters) {
        return new Random().ints(length, 0, characters.length())
                .mapToObj(i -> String.valueOf(characters.charAt(i)))
                .collect(Collectors.joining());
    }
}
