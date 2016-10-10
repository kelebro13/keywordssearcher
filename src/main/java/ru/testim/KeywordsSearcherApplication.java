package ru.testim;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.testim.util.ConsoleHelper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class KeywordsSearcherApplication {

    private static final Logger LOG = LoggerFactory.getLogger(KeywordsSearcherApplication.class);

    public static void main(String[] args) throws IOException{

        ConsoleHelper.writeHeader();
        while (true) {
            String path = ConsoleHelper.readString();
            if("exit".equals(path)) {
                break;
            }
            try {
                Set<String> keywords = FileInputKeyword.getKeywords(path);
                if (keywords.isEmpty()) {
                    LOG.debug(String.format("Файл %s не содержит ключевых слов", path));
                    ConsoleHelper.writeMessage("Файл не содержит ключевых слов. Выберите другой файл.");
                    continue;
                }
                ConsoleHelper.writeMessage("Запуск поиска ссылов...");
                Map<String, Integer> linksStatistic = YandexApiSearcher.getLinksStatistics(keywords);
                if(linksStatistic.isEmpty()){
                    LOG.debug(String.format("Ссылок по данным ключевым словам s не найдено.", keywords.toString()));
                    ConsoleHelper.writeMessage("Ссылок по данным ключевым словам не найдено.");
                    break;
                }
                saveLinksStatistics(linksStatistic);
                ConsoleHelper.writeMessage("Поиск завершен.");
                break;
            } catch (Exception e) {
                ConsoleHelper.writeMessage(e.getMessage());
            }
            ConsoleHelper.writeMessage("Повторите операцию:");
        }
    }

    public static void saveLinksStatistics(Map<String, Integer> linksStatistic) throws IOException{

        List<String> outputDate = linksStatistic.entrySet().stream().map(pair -> pair.getKey() + " = " + pair.getValue()).collect(Collectors.toList());
        Path file = Paths.get("LinksStatistic.txt");
        Files.write(file, outputDate, Charset.forName("UTF-8"));
    }
}
