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

    public List<String> findWordSquares(List<String> words, Map<Character, Long> originalFrequencyMap) {
        TrieNode root = new TrieNode();
        for (String word : words)
            insert(root, word);
        List<List<String>> squares = new ArrayList<>();
        int length = words.get(0).length();
        String[] finished = new String[length];
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

    public List<String> validateCharacterCount(List<List<String>> words, Map<Character, Long> originalFrequencyMap){
        List<String> returnList = new ArrayList<>();
        for (List<String> wordSquare : words){
            StringBuilder sb = new StringBuilder();
            wordSquare.forEach(sb::append);
            if(Utils.createFreuqencyMap(sb.toString()).equals(originalFrequencyMap)){
                returnList = wordSquare;
            }
        }
        return returnList;
    }

    public void insert(TrieNode root, String word) {
        TrieNode node = root;
        int length = word.length();
        for (int i = 0; i < length; i++) {
            char letter = word.charAt(i);
            int index = letter - 'a';
            if (node.next[index] == null)
                node.next[index] = new TrieNode();
            node = node.next[index];
        }
        node.word = word;
    }

    public void search(TrieNode current, TrieNode root, List<List<String>> board, int row, int column, int length, String[] finished, List<List<String>> squares) {
        if (row == length)
            squares.add(new ArrayList<String>(Arrays.asList(finished)));
        else if (column == length) {
            finished[row] = current.word;
            search(root, root, board, row + 1, 0, length, finished, squares);
        } else if (column < row) {
            String letter = board.get(row).get(column);
            TrieNode nextNode = current.next[letter.charAt(0) - 'a'];
            if (nextNode != null)
                search(nextNode, root, board, row, column + 1, length, finished, squares);
        } else {
            for (char c = 'a'; c <= 'z'; c++) {
                int index = c - 'a';
                if (current.next[index] != null) {
                    if (row == 0 && root.next[index] == null)
                        continue;
                    board.get(row).set(column, String.valueOf(c));
                    board.get(column).set(row, String.valueOf(c));
                    search(current.next[index], root, board, row, column + 1, length, finished, squares);
                }
            }
        }
    }
}