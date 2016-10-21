package ru.testim;

import org.junit.Assert;
import org.junit.Test;
import ru.testim.datatest.KeywordsDataTest;
import ru.testim.datatest.LinksStatisticsDataTest;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class KeywordsSearcherApplicationTest {

    @Test
    public void testGetKeywordsFileUtf8() throws Exception {
        Set<String> keywords = KeywordsSearcherApplication.getKeywordsFromFile(Paths.get("testfile\\KeywordsTest_Utf8.txt"));
        Assert.assertArrayEquals(KeywordsDataTest.keywordsTestSet.toArray(), keywords.toArray());
    }

    @Test
    public void testGetKeywordsFileWindows1251() throws Exception {
        Set<String> keywords = KeywordsSearcherApplication.getKeywordsFromFile(Paths.get("testfile\\KeywordsTest_windows_1251.txt"));
        Assert.assertArrayEquals(KeywordsDataTest.keywordsTestSet.toArray(), keywords.toArray());
    }

    @Test(expected = NoSuchFileException.class)
    public void testGetKeywordsNotFile() throws Exception {
        KeywordsSearcherApplication.getKeywordsFromFile(Paths.get("testfile\\KeywordsTest_not_file.txt"));
    }

    @Test
    public void testGetKeywordsWrong() throws Exception {
        Set<String> keywords = KeywordsSearcherApplication.getKeywordsFromFile(Paths.get("testfile\\KeywordsTest_wrong.txt"));
        Assert.assertEquals(1, keywords.size());
    }

    @Test
    public void testSaveLinksStatistics() throws Exception {
        Map<String, Integer> links = LinksStatisticsDataTest.liksStatisticsTestMap;
        KeywordsSearcherApplication.saveLinksStatistics(links);

        List<String> actual = Files.lines(Paths.get("LinksStatistic.txt")).collect(Collectors.toList());

        List<String> expected = LinksStatisticsDataTest.liksStatisticsTestMap.entrySet().stream().map(pair -> pair.getKey() + " = " + pair.getValue()).collect(Collectors.toList());
        Assert.assertEquals(expected, actual);
    }

}