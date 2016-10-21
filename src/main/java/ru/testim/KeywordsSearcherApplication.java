package ru.testim;


import com.beust.jcommander.JCommander;
import org.apache.any23.encoding.TikaEncodingDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.testim.util.ConsoleHelper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class KeywordsSearcherApplication {

    private static final Logger LOG = LoggerFactory.getLogger(KeywordsSearcherApplication.class);
    private static final String REGEXP = "[А-я\\w]+";

    public static void main(String[] args) throws IOException{

        ConsoleHelper.writeHeader();
        JCommanderSetting setting = new JCommanderSetting();
        JCommander commander = new JCommander(setting);
        while (true) {
            String[] path = ConsoleHelper.readString().split(" ");
            if("exit".equals(path[0])) {
                break;
            }
            try {
                commander.parse(path);
                if (setting.isHelp()) {
                    commander.usage();
                    setting.setHelp(false);
                } else {
                    try {
                        Set<String> keywords = getKeywordsFromFile(setting.getPath().get(0));
                        if (keywords.isEmpty()) {
                            LOG.debug(String.format("Файл %s не содержит ключевых слов", path));
                            ConsoleHelper.writeMessage("Файл не содержит ключевых слов. Выберите другой файл.");
                            continue;
                        }
                        ConsoleHelper.writeMessage("Запуск поиска ссылов...");
                        Map<String, Integer> linksStatistic = getLinksStatistic(keywords, setting);
                        if (linksStatistic.isEmpty()) {
                            LOG.debug(String.format("Ссылок по данным ключевым словам %s не найдено.", keywords.toString()));
                            ConsoleHelper.writeMessage("Ссылок по данным ключевым словам не найдено.");
                            break;
                        }
                        saveLinksStatistics(linksStatistic);
                        ConsoleHelper.writeMessage("Поиск завершен.");
                        break;
                    } catch (Exception e) {
                        ConsoleHelper.writeMessage(e.getMessage());
                    }
                }
            } catch (Exception e) {
                LOG.debug(e.getLocalizedMessage());
            }



            ConsoleHelper.writeMessage("Повторите операцию:");
        }
    }

    public static Set<String> getKeywordsFromFile(Path filePath) throws IOException, IllegalArgumentException {

        Charset charset = Charset.forName(new TikaEncodingDetector().guessEncoding(Files.newInputStream(filePath)));
        Set<String> keywordSet = Files.lines(filePath, charset).peek(s -> {
            if (!s.matches(REGEXP)) {
                LOG.debug("Неверные данные в строке: " + s);
            }
        }).filter(s -> s.matches(REGEXP)).peek(s -> ConsoleHelper.writeMessage("Добавлено ключевое слово: " + s))
                .collect(Collectors.toCollection(HashSet::new));
        return keywordSet;
    }

    public static Map<String, Integer> getLinksStatistic(Set<String> keywords, JCommanderSetting setting) throws IOException {
        YandexApiSearcher searcher = new YandexApiSearcher();
        if (setting.isHaveUrlProxy()) {
            searcher.setUrlProxy(setting.getUrlProxy());
            if (setting.isHavePortProxy()) {
                searcher.setPortProxy(setting.getPortProxy());
            }
        }
        Map<String, Integer> linksStatistic = searcher.getLinksStatistics(keywords);
        if(linksStatistic.isEmpty()){
            return Collections.emptyMap();
        }
        return linksStatistic;
    }

    public static void saveLinksStatistics(Map<String, Integer> linksStatistic) throws IOException{

        List<String> outputDate = linksStatistic.entrySet().stream().map(pair -> pair.getKey() + " = " + pair.getValue()).collect(Collectors.toList());
        Path file = Paths.get("LinksStatistic.txt");
        Files.write(file, outputDate, Charset.forName("UTF-8"));
    }
}
