package com.company.java;

import java.util.*;

public class WordSquare {

    boolean verbose = false;

    class TrieNode {
        TrieNode[] next;
        String word = null;

        public TrieNode() {
            next = new TrieNode[26];
        }
    }

    public List<String> findWordSquares(List<String> wordSquares, Map<Character, Long> originalFrequencyMap) {
        TrieNode root = new TrieNode();
        for (String word : wordSquares)
            insert(root, word);
        List<List<String>> squares = new ArrayList<>(); //return variable
        int length = wordSquares.get(0).length(); //initial root node word length
        String[] finished = new String[length]; //recursion stopping condition, if the
        List<List<String>> board = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            board.add(new ArrayList<>(Collections.nCopies(length, "")));
        }
        search(root, root, board, 0, 0, length, finished, squares);
        if (verbose) {
            System.out.println("Found potential word squares, validating against initial character frequency map \n" + squares);
        }
        return validateCharacterCount(squares, originalFrequencyMap);
    }

    public List<String> validateCharacterCount(List<List<String>> wordSquares, Map<Character, Long> originalFrequencyMap){
        List<String> returnList = new ArrayList<>();
        for (List<String> wordSquare : wordSquares){  //for every wordsquare found
            StringBuilder sb = new StringBuilder();
            wordSquare.forEach(sb::append); //concatenate each string in wordsquare
            if(Utils.createFreuqencyMap(sb.toString()).equals(originalFrequencyMap)){ //create frequency map and compare to original map
                returnList = wordSquare; //return matched square
            }
        }
        return returnList;
    }

    public void insert(TrieNode root, String word) {
        TrieNode node = root;
        int length = word.length(); //get the length of the word
        for (int i = 0; i < length; i++) {
            char letter = word.charAt(i);
            int index = letter - 'a'; // calculate the index of the letter in the TrieNode's children array
            if (node.next[index] == null)
                node.next[index] = new TrieNode(); // create a new TrieNode if no next valid node
            node = node.next[index]; // move to the next TrieNode
        }
        node.word = word; // sets nodes word
    }

    public void search(TrieNode current, TrieNode root, List<List<String>> board, int row, int column, int wordLength, String[] finished, List<List<String>> wordSquares) {
        if (row == wordLength)
            wordSquares.add(new ArrayList<String>(Arrays.asList(finished))); // add the current finished words as a square if current searched row is wordLength of board
        else if (column == wordLength) {
            finished[row] = current.word; // add the current word to the finished words array if current searched column is the wordLength of the board
            search(root, root, board, row + 1, 0, wordLength, finished, wordSquares); // and continue the search on the next row
        } else if (column < row) { // if the current column is less than the current row,
            String letter = board.get(row).get(column); // get the letter at the current position on the board
            TrieNode nextNode = current.next[letter.charAt(0) - 'a']; // retrieve the next TrieNode based on the letter
            if (nextNode != null)
                search(nextNode, root, board, row, column + 1, wordLength, finished, wordSquares);
        } else {
            for (char c = 'a'; c <= 'z'; c++) { //iterate through the alphabet
                int index = c - 'a';
                if (current.next[index] != null) { // if the next TrieNode is valid
                    if (row == 0 && root.next[index] == null) // if it is the first row and the letter is not in the trie
                        continue; // skip this iteration
                    board.get(row).set(column, String.valueOf(c)); // set the letter on the current row and column on the board
                    board.get(column).set(row, String.valueOf(c));
                    search(current.next[index], root, board, row, column + 1, wordLength, finished, wordSquares); // continue the search on the corresponding TrieNode
                }
            }
        }
    }
}