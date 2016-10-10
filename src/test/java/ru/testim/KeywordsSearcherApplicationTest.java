package ru.testim;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static ru.testim.KeywordsSearcherApplication.getKeywordsFromFile;
import static ru.testim.datatest.KeywordsDataTest.keywordsTestSet;
import static ru.testim.datatest.LinksStatisticsDataTest.liksStatisticsTestMap;

public class KeywordsSearcherApplicationTest {

    @Test
    public void testGetKeywordsFileUtf8() throws Exception {
        Set<String> keywords = getKeywordsFromFile("testfile\\KeywordsTest_Utf8.txt");
        assertArrayEquals(keywordsTestSet.toArray(), keywords.toArray());
    }

    @Test
    public void testGetKeywordsFileWindows1251() throws Exception {
        Set<String> keywords = getKeywordsFromFile("testfile\\KeywordsTest_windows_1251.txt");
        assertArrayEquals(keywordsTestSet.toArray(), keywords.toArray());
    }

    @Test(expected = FileNotFoundException.class)
    public void testGetKeywordsNotFile() throws Exception {
        getKeywordsFromFile("testfile\\KeywordsTest_not_file.txt");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetKeywordsWrong() throws Exception {
        getKeywordsFromFile("testfile\\KeywordsTest_wrong.txt");
    }


    @Test
    public void testSaveLinksStatistics() throws Exception {
        Map<String, Integer> links = liksStatisticsTestMap;
        KeywordsSearcherApplication.saveLinksStatistics(links);

        List<String> actual = Files.lines(Paths.get("LinksStatistic.txt")).collect(Collectors.toList());

        List<String> expected = liksStatisticsTestMap.entrySet().stream().map(pair -> pair.getKey() + " = " + pair.getValue()).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

}