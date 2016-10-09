package ru.testim;


import org.apache.any23.encoding.TikaEncodingDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FileInputKeyword {

    private static final Logger LOG = LoggerFactory.getLogger(FileInputKeyword.class);
    private static final String REGEXP = "[А-я\\w]+";

    private FileInputKeyword(){}

    public static Set<String> getKeywords(String filePath) throws IOException, IllegalArgumentException {

        Charset charset = Charset.forName(new TikaEncodingDetector().guessEncoding(new FileInputStream(filePath)));
        Set<String> keywordSet = Files.lines(Paths.get(filePath), charset).collect(Collectors.toCollection(HashSet::new));

        if(!keywordSet.stream().allMatch(s -> s.matches(REGEXP))){
            LOG.debug(String.format("Файл %s содержит неверные данные", filePath));
            throw new IllegalArgumentException("Файл содержит неверные данные");
        }
        return keywordSet;
    }


}
