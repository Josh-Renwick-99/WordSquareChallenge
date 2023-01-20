package com.company.java;

import java.util.ArrayList;
import java.util.List;

public class HashTable {
    private List<String>[] table;
    private int size;

    public HashTable(int size) {
        this.size = size;
        table = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new ArrayList<>();
        }
    }

    private int hash(String word) {
        int hash = 0;
        for (int i = 0; i < word.length(); i++) {
            hash = (hash + (int) word.charAt(i)) % size;
        }
        return hash;
    }

    public void add(String word) {
        int index = hash(word);
        table[index].add(word);
    }

    public boolean contains(String word) {
        int index = hash(word);
        return table[index].contains(word);
    }
}











