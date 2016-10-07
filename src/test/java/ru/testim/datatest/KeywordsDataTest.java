package ru.testim.datatest;


import java.util.HashSet;
import java.util.Set;

public class KeywordsDataTest {

    public static final Set<String> keywordsTestSet = new HashSet<>();

    static {
        keywordsTestSet.add("java");
        keywordsTestSet.add("scala");
        keywordsTestSet.add("привет");
    }

    private KeywordsDataTest(){}
}
