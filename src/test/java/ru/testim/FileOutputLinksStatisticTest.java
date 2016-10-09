package ru.testim;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static ru.testim.datatest.LinksStatisticsDataTest.liksStatisticsTestMap;


public class FileOutputLinksStatisticTest {

    @Test
    public void testSaveLinksStatistics() throws Exception {
        Map<String, Integer> links = liksStatisticsTestMap;
        FileOutputLinksStatistic.saveLinksStatistics(links);

        List<String> actual = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("LinksStatistic.txt")))){
            while (reader.ready()) {
                actual.add(reader.readLine());
            }
        }

        List<String> expected = liksStatisticsTestMap.entrySet().stream().map(pair -> pair.getKey() + " = " + pair.getValue()).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

}