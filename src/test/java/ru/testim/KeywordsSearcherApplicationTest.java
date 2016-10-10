package ru.testim;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static ru.testim.datatest.LinksStatisticsDataTest.liksStatisticsTestMap;

public class KeywordsSearcherApplicationTest {

    @Test
    public void testSaveLinksStatistics() throws Exception {
        Map<String, Integer> links = liksStatisticsTestMap;
        KeywordsSearcherApplication.saveLinksStatistics(links);

        List<String> actual = Files.lines(Paths.get("LinksStatistic.txt")).collect(Collectors.toList());

        List<String> expected = liksStatisticsTestMap.entrySet().stream().map(pair -> pair.getKey() + " = " + pair.getValue()).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

}