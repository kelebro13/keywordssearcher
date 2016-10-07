package ru.testim;


import org.apache.any23.encoding.TikaEncodingDetector;
import ru.testim.exception.KeywordWrongException;
import ru.testim.model.KeywordList;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileInputKeyword {

    private static final String REGEXP = "[А-я\\w]+";
    private static Pattern pattern = Pattern.compile(REGEXP);

    private FileInputKeyword(){}

    public static KeywordList getKeywords(String filePath) throws IOException, KeywordWrongException {

        KeywordList keywords = new KeywordList();
        Set<String> keywordSet = new HashSet<>();
        Charset charset = Charset.forName(new TikaEncodingDetector().guessEncoding(new FileInputStream(filePath)));
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset));
        String line;
        Matcher matcher;
        while (reader.ready()) {
            line = reader.readLine();
            matcher = pattern.matcher(line);
            if (matcher.matches()) {
                keywordSet.add(line);
            } else {
                throw new KeywordWrongException("Файл содержит неверные данные");
            }
        }
        reader.close();
        keywords.setKeywords(keywordSet);
        return keywords;
    }


}
