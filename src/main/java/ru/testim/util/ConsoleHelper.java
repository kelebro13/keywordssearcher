package ru.testim.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private ConsoleHelper(){}

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static void writeHeader() throws IOException{
        writeMessage(
                "==================================================================================\n\n" +
                "                         KEYWORDS SEARCHER APPLICATION                            \n\n" +
                "==================================================================================\n\n" +
                "Введите путь к файлу с ключевыми словами (для закрытия прокраммы введите \"exit\")"
        );
    }


}
