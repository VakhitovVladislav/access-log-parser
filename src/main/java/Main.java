package main.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter text and click <Enter>: ");
        String text = new Scanner(System.in).nextLine();
        System.out.println("Text length: " + text.length());
    }
}