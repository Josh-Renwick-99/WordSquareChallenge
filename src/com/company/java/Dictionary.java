package com.company.java;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

@Data
public class Dictionary {

    private List<String> finalConfirmedWords;
    boolean verbose = false;
    HashTable wordHashTable; //amount of lines in dictionary.txt


    public Dictionary(){
        this.wordHashTable = loadDictionary();
    }

    public Dictionary(HashTable hashTable){
        this.wordHashTable = hashTable;
    }

    public void generateWordSquare(String input){
        List<String> generatedWords = new ArrayList<>();
        List<String> confirmedWords = new ArrayList<>();
        OptionalInt result = input.chars()
                .filter(Character::isDigit)
                .map(c -> c - '0')
                .findFirst();
        Map<Character, Long> freqMap = Utils.createFreuqencyMap(input);
        if (result.isPresent()) {
            Integer numWords = result.getAsInt();
            generateWords(freqMap, numWords, generatedWords);
        }
        generatedWords.forEach(word -> {
            if (wordHashTable.contains(word)){
                confirmedWords.add(word);
            }
        });
        if (verbose) {
            System.out.println("Found words potential words within input string \n" + confirmedWords);
        }
        finalConfirmedWords = confirmedWords;
    }

    public static void generateWords(Map<Character, Long> frequencyMap, int wordLength, List<String> generatedWordList) {
        // Create a character array to store the current word
        char[] word = new char[wordLength];
        // Call the recursive function to generate all possible words
        generateWordsHelper(frequencyMap, word, 0, generatedWordList);
    }

    public static void generateWordsHelper(Map<Character, Long> frequencyMap, char[] word, int index, List<String> generatedWordList) {
        // Base case: if the current index is equal to the word length, print the word
        if (index == word.length) {
            StringBuilder finalWord = new StringBuilder();
            for (char c: word){
                finalWord.append(c);
            }
            generatedWordList.add(finalWord.toString());
            return;
        }
        // Iterate through the frequency map
        for (Map.Entry<Character, Long> entry : frequencyMap.entrySet()) {
            char c = entry.getKey();
            Long frequency = entry.getValue();

            // Check if the character has remaining frequency
            if (frequency > 0) {
                // Add the character to the current word and decrease its frequency
                word[index] = c;
                frequencyMap.put(c, frequency - 1);
                // Recursively call the function with the updated frequency map and word
                generateWordsHelper(frequencyMap, word, index + 1, generatedWordList);
                // Backtrack: increase the frequency of the character and remove it from the current word
                frequencyMap.put(c, frequency);
            }
        }
    }

    public static HashTable loadDictionary(){
        String FILEPATH = "src/com/company/resources/dictionaries/words.txt";
        HashTable hashTable = new HashTable(225114);
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    FILEPATH));
            String str;
            while ((str = in.readLine()) != null) {
                hashTable.add(str);
            }
            in.close();
        } catch (IOException e) {
        }
        return hashTable;
    }
}
