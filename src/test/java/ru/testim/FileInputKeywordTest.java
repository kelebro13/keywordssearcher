package ru.testim;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static ru.testim.FileInputKeyword.getKeywords;
import static ru.testim.datatest.KeywordsDataTest.keywordsTestSet;


public class FileInputKeywordTest {


    @Test
    public void testGetKeywordsFileUtf8() throws Exception {
        Set<String> keywords = getKeywords("testfile\\KeywordsTest_Utf8.txt");
        assertArrayEquals(keywordsTestSet.toArray(), keywords.toArray());
    }

    @Test
    public void testGetKeywordsFileWindows1251() throws Exception {
        Set<String> keywords = getKeywords("testfile\\KeywordsTest_windows_1251.txt");
        assertArrayEquals(keywordsTestSet.toArray(), keywords.toArray());
    }

    @Test(expected = FileNotFoundException.class)
    public void testGetKeywordsNotFile() throws Exception {
       getKeywords("testfile\\KeywordsTest_not_file.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetKeywordsWrong() throws Exception {
        getKeywords("testfile\\KeywordsTest_wrong.txt");
    }

}