package ru.testim;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.testim.model.KeywordList;
import ru.testim.model.LinksStatistic;
import ru.testim.util.ConsoleHelper;

import java.io.IOException;

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
                KeywordList keywords = FileInputKeyword.getKeywords(path);
                if (keywords.isEmpty()) {
                    LOG.debug(String.format("Файл %s не содержит ключевых слов", path));
                    ConsoleHelper.writeMessage("Файл не содержит ключевых слов. Выберите другой файл.");
                    continue;
                }
                ConsoleHelper.writeMessage("Запуск поиска ссылов...");
                LinksStatistic linksStatistic = SearcherLink.getLinksStatistics(keywords);
                if(linksStatistic.isEmpty()){
                    LOG.debug(String.format("Ссылок по данным ключевым словам s не найдено.", keywords.getKeywords().toString()));
                    ConsoleHelper.writeMessage("Ссылок по данным ключевым словам не найдено.");
                    break;
                }
                FileOutputLinksStatistic.saveLinksStatistics(linksStatistic);
                ConsoleHelper.writeMessage("Поиск завершен.");
                break;
            } catch (Exception e) {
                ConsoleHelper.writeMessage(e.getMessage());
            }
            ConsoleHelper.writeMessage("Повторите операцию:");
        }
    }
}
