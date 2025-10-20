package main.java;

import java.io.*;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {
    public static double calculatePersent(long allStings, long checkString) {
        return ((double) checkString / allStings) * 100;
    }

    public static void main(String[] args) {
        //старый код
        long tryCount = 0;
        String path;
        File file;
        long count = 0;
        long botCount = 0;
        long yandexCount = 0;
        long googleCount = 0;


        List<String> bots = new ArrayList<>();
        while (true) {
            path = new Scanner(System.in).nextLine();
            file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (isDirectory) {
                System.out.println("it is a directory");
            } else if (fileExists) {
                System.out.println("SUCCESS->>> Your file is correct");
                tryCount++;
                System.out.println("The file is " + tryCount);
                // Задание #1. Обработка исключений
                //TODO разобраться почему в файле около 7к строк, но каунтер возвращает 191 тысячу
                try (FileReader fileReader = new FileReader(path)) {
                    BufferedReader reader = new BufferedReader(fileReader);
                    List<String> lines = reader.lines().collect(Collectors.toList());
                    count = lines.size();
                    lines.stream().anyMatch(str -> {
                        if (str.length() > 1024) {
                            throw new RuntimeException("ERROR->>>String length more than 1024 symbols! STRING - > " + str);
                        }
                        return true;
                    });
                    //Получаем список всех строк, у которых есть блок User Agent и режем результат по символу ';'
                    //затем все кладем в String[] и это все в List
                    System.out.println("START->>> parsing your file");
                    List<String[]> userAgentParts = lines.stream()
                            .filter(s -> s.contains("(compatible;"))
                            .map(s -> s.substring(s.indexOf("(compatible;")))
                            .map(s -> s.split(";"))
                            .map(s -> Arrays.stream(s)
                                    .map(String::trim)
                                    .toArray(String[]::new))
                            .collect(Collectors.toList());
                    botCount = userAgentParts.size();
                    //TODO понять как в стриме добраться до элемента массива String[1]
                    for (String[] parts : userAgentParts) {
                        if (parts.length >= 2) {
                            bots.add(parts[1]);
                        }
                    }
                    bots.size();
                    yandexCount = bots.stream()
                            .filter(s -> s.contains("/"))
                            .map(s -> s.substring(0, s.indexOf('/')))
                            .filter(s -> s.contains("YandexBot"))
                            .count();
                    googleCount = bots.stream()
                            .filter(s -> s.contains("/"))
                            .map(s -> s.substring(0, s.indexOf('/')))
                            .filter(s -> s.contains("Googlebot"))
                            .count();

                    count = lines.size();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println("Stings count: " + count);
                System.out.println("Persent of Yandexbot is: " + calculatePersent(count, yandexCount));
                System.out.println("Persent of Googlebot is: " + calculatePersent(count, googleCount));
            } else {
                System.out.println("file is not exists or it is ");
            }
        }
    }
}