package com.company.java;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {
    public static <T> Map<Character, Long> createFreuqencyMap(String input) {
        List<Character> elements = input.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        return elements.stream()
                .filter(c -> !Character.isWhitespace(c) && !Character.isDigit(c)) //extra sanitation to filter out white space and digits
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                HashMap::new,
                                Collectors.counting() //maps to HashMap using Character c as key and count of c as value
                        )
                );
    }

    public boolean containsChars(Map<Character, Long> map, char[] chars) {
        for (Character c : chars) {
            if (!map.containsKey(c) || map.get(c) < 1) {
                return false;
            }
        }
        return true;
    }
}
