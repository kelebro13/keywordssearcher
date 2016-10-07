package ru.testim;


import ru.testim.model.KeywordList;
import ru.testim.model.LinksStatistic;
import ru.testim.util.ConsoleHelper;

import java.io.IOException;

public class KeywordsSearcherApplication {


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
                    ConsoleHelper.writeMessage("Файл не содержит ключевых слов. Выберите другой файл.\n");
                    continue;
                }
                ConsoleHelper.writeMessage("Запуск поиска ссылов...");
                LinksStatistic linksStatistic = SearcherLink.getLinksStatistics(keywords);
                if(linksStatistic.isEmpty()){
                    ConsoleHelper.writeMessage("Ссылок по данным ключевым словам не найдено.\n");
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
