package main.java;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int tryCount =0;
        while (true){
            String path = new Scanner(System.in).nextLine();

            File file = new File(path);
            boolean fileExists = file.exists();
            boolean  isDirectory = file.isDirectory();
            if (isDirectory){
                System.out.println("it is a directory");
            } else if (fileExists) {
                System.out.println("The path is correct");
                tryCount++;
                System.out.println("The file is " + tryCount);
            }else {
                System.out.println("file is not exists or it is ");
            }
        }
    }
}