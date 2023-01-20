package com.company.java;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean repeat = true;
        System.out.println("This is a word square solver");
        System.out.println("Enter a string in the format N N^2 characters");
        System.out.println("Such as 4 abcdefghijklmnop, where 4 is the length of each word in the square");
        System.out.println("The program will generate a valid word square if it is possible");

        while (repeat) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your input string in the correct format");
            String input = scanner.nextLine();
            if (sanitiseInput(input)) {
                System.out.println(String.format("Generating possible word squares for '%s'", input));
                List<String> actualOutput = wordSquareSolver(input);
                if (!actualOutput.isEmpty()) {
                    System.out.println(String.format("Found word square(s) for '%s'", input));
                    actualOutput.forEach(System.out::println);
                } else {
                    System.out.println(String.format("No possible solution found for '%s'", input));
                }
                System.out.println("Would you like to generate another word square? Answer y/n");
                if (!(scanner.next().equals("y") || scanner.next().equals("Y"))) {
                    repeat = false;
                    scanner.close();
                }
            }
        }
        System.out.println("Exiting the program");
    }

    public static List<String> wordSquareSolver(String input){
        Dictionary dictionary = new Dictionary();
        dictionary.generateWordSquare(input);
        Map<Character, Long> freqMap = Utils.createFreuqencyMap(input);
        WordSquare wordSquare = new WordSquare();
        return wordSquare.findWordSquares(dictionary.getFinalConfirmedWords(), freqMap);
    }

    public static boolean sanitiseInput(String input){
        try {
            List<String> parts = List.of(input.split(" "));
            int number = Integer.parseInt(parts.get(0));
            String string = parts.get(1);
            return string.length() == (number * number);
        } catch (NumberFormatException e){
            return false;
        }
    }
}
