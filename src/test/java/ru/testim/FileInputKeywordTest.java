package ru.testim;

import org.junit.Test;
import ru.testim.exception.KeywordWrongException;
import ru.testim.model.KeywordList;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static ru.testim.FileInputKeyword.getKeywords;
import static ru.testim.datatest.KeywordsDataTest.*;


public class FileInputKeywordTest {


    @Test
    public void testGetKeywordsFileUtf8() throws Exception {
        KeywordList keywords = getKeywords("testfile\\KeywordsTest_Utf8.txt");
        assertEquals(keywordsTestSet, keywords.getKeywords());
    }

    @Test
    public void testGetKeywordsFileWindows1251() throws Exception {
        KeywordList keywords = getKeywords("testfile\\KeywordsTest_windows_1251.txt");
        assertEquals(keywordsTestSet, keywords.getKeywords());
    }

    @Test(expected = FileNotFoundException.class)
    public void testGetKeywordsNotFile() throws Exception {
       getKeywords("testfile\\KeywordsTest_not_file.txt");
    }

    @Test(expected = KeywordWrongException.class)
    public void testGetKeywordsWrong() throws Exception {
        getKeywords("testfile\\KeywordsTest_wrong.txt");
    }

}