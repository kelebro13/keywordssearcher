package ru.testim;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileOutputLinksStatistic {

    private FileOutputLinksStatistic(){}


    public static void saveLinksStatistics(Map<String, Integer> linksStatistic) throws IOException{

        List<String> outputDate = linksStatistic.entrySet().stream().map(pair -> pair.getKey() + " = " + pair.getValue()).collect(Collectors.toList());
        Path file = Paths.get("LinksStatistic.txt");
        Files.write(file, outputDate, Charset.forName("UTF-8"));
    }
}
