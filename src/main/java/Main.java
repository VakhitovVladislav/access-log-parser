package main.java;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        //старый код
        long tryCount = 0;
        String path;
        File file;
        long count = 0;
        long lineLengthMin = 0;
        long lineLengthMax = 0;
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
                try(FileReader fileReader = new FileReader(path)) {
                    BufferedReader reader = new BufferedReader(fileReader);
                    List<String> lines = reader.lines().collect(Collectors.toList());
                    lines.stream().allMatch(str-> {if (str.length() > 1024) {
                        throw new RuntimeException("String length more than 1024 symbols! STRING - > " + str);
                    }
                    return true;
                    });
                    lineLengthMin = lines.stream()
                            .mapToLong(String::length)
                            .min()
                            .orElse(0);
                    lineLengthMax = lines.stream()
                            .mapToLong(String::length)
                            .max()
                            .orElse(0);
                    count = lines.size();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println("Stings count: " + count);
                System.out.println("the longest line: " + lineLengthMax);
                System.out.println("the shortest line: " + lineLengthMin);
            } else {
                System.out.println("file is not exists or it is ");
            }
        }



    }
}