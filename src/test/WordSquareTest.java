package test;

import com.company.java.Dictionary;
import com.company.java.HashTable;
import com.company.java.Utils;
import com.company.java.WordSquare;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Data
public class WordSquareTest {

    String FILEPATH = "src/com/company/resources/dictionaries/words.txt";
    HashTable hashWordTable;

    @Before
    public void beforeScenario(){
        hashWordTable = new HashTable(225114);
        try {
            BufferedReader in = new BufferedReader(new FileReader(
                    FILEPATH));
            String str;
            while ((str = in.readLine()) != null) {
                hashWordTable.add(str);
            }
            in.close();
        } catch (IOException e) {
        }
    }

    @Test
    public void find4WordsTest(){
        Dictionary dictionary = new Dictionary(hashWordTable);
        final String input = "4 eeeeddoonnnsssrv";
        dictionary.findAnagrams(input);
        Map<Character, Long> freqMap = Utils.createFreuqencyMap(input);
        WordSquare wordSquare = new WordSquare();
        List<String> actualOutput = wordSquare.findWordSquares(dictionary.getConfirmedAnagrams(), freqMap);
        List<String> expectedOutput = Arrays.asList("rose", "oven", "send", "ends");
        assertEquals(actualOutput, expectedOutput);
        printSquares(actualOutput, input);
    }

    @Test
    public void findAlternative4WordsTest(){
        Dictionary dictionary = new Dictionary(hashWordTable);
        final String input = "4 aaccdeeeemmnnnoo";
        dictionary.findAnagrams(input);
        Map<Character, Long> freqMap = Utils.createFreuqencyMap(input);
        WordSquare wordSquare = new WordSquare();
        List<String> actualOutput = wordSquare.findWordSquares(dictionary.getConfirmedAnagrams(), freqMap);
        List<String> expectedOutput = Arrays.asList("moan", "once", "acme", "need");
        assertEquals(actualOutput, expectedOutput);
        printSquares(actualOutput, input);
    }

    @Test
    public void find5WordsTest(){
        Dictionary dictionary = new Dictionary(hashWordTable);
        final String input = "5 aaaeeeefhhmoonssrrrrttttw";
        dictionary.findAnagrams(input);
        Map<Character, Long> freqMap = Utils.createFreuqencyMap(input);
        WordSquare wordSquare = new WordSquare();
        List<String> actualOutput = wordSquare.findWordSquares(dictionary.getConfirmedAnagrams(), freqMap);
        List<String> expectedOutput = Arrays.asList("feast", "earth", "armor", "stone", "threw");
        assertEquals(actualOutput, expectedOutput);
        printSquares(actualOutput, input);
    }

    @Test
    public void findAlternative5WordTest(){
        Dictionary dictionary = new Dictionary(hashWordTable);
        final String input = "5 aabbeeeeeeeehmosrrrruttvv";
        dictionary.findAnagrams(input);
        Map<Character, Long> freqMap = Utils.createFreuqencyMap(input);
        WordSquare wordSquare = new WordSquare();
        List<String> actualOutput = wordSquare.findWordSquares(dictionary.getConfirmedAnagrams(), freqMap);
        List<String> expectedOutput = Arrays.asList("heart", "ember", "above", "revue", "trees");
        assertEquals(actualOutput, expectedOutput);
        printSquares(actualOutput, input);
    }

    @Test
    public void find7WordTest(){
        Dictionary dictionary = new Dictionary(hashWordTable);
        final String input = "7 aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy";
        dictionary.findAnagrams(input);
        Map<Character, Long> freqMap = Utils.createFreuqencyMap(input);
        WordSquare wordSquare = new WordSquare();
        List<String> actualOutput = wordSquare.findWordSquares(dictionary.getConfirmedAnagrams(), freqMap);
        List<String> expectedOutput = Arrays.asList("bravado", "renamed", "analogy", "valuers", "amoebas", "degrade", "odyssey");
        assertEquals(actualOutput, expectedOutput);
        printSquares(actualOutput, input);
    }

    @Test
    public void findNoSquareTest(){
        Dictionary dictionary = new Dictionary(hashWordTable);
        final String input = "4 eeeedzoobnnsssrv";
        dictionary.findAnagrams(input);
        Map<Character, Long> freqMap = Utils.createFreuqencyMap(input);
        WordSquare wordSquare = new WordSquare();
        List<String> actualOutput = wordSquare.findWordSquares(dictionary.getConfirmedAnagrams(), freqMap);
        assertTrue(actualOutput.isEmpty());
        printSquares(actualOutput, input);
    }

    public void printSquares(List<String> square, String input){
        System.out.println(String.format("Final squares found for input '%s'", input));
        square.forEach(s -> {
            System.out.println(s);
        });
    }

}
