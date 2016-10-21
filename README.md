# Keywords Searcher Application

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/18e358ed9b7b47f9b8fe1506b5efe65a)](https://www.codacy.com/app/tgumerov/keywordssearcher?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=kelebro13/keywordssearcher&amp;utm_campaign=Badge_Grade)

[Тестовое задание](task.md)

### Установка

```
git clone https://github.com/kelebro13/keywordssearcher.git
```

### Запуск (из директория проекта)

```
$ mvn clean package
$ mvc exec:java
```


### Команды

```
"путь к файлу где хранятся ключевые слова" [options] // пример строки: D\test.txt -u http://www.test.ru -p 8080

--help //вызов подсказки

exit // выход из программы
```

Результат выполнения программы сохраняется в файл LinksStatistic.txt, в директории проекта.


### Результат

Результат выполнения программы сохраняется в файл LinksStatistic.txt, в директории проекта.