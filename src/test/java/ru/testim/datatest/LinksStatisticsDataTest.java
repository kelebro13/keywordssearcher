package ru.testim.datatest;

import java.util.HashMap;
import java.util.Map;

public class LinksStatisticsDataTest {

    public static final Map<String, Integer> liksStatisticsTestMap = new HashMap<>();

    static {
        liksStatisticsTestMap.put("vk.com", 2);
        liksStatisticsTestMap.put("cyberforum.ru", 1);
        liksStatisticsTestMap.put("twitter.com", 1);
    }

    private LinksStatisticsDataTest(){}
}
