package main.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Получаем первое число
        System.out.println("Please enter you first Number");
        int firstNumber = new Scanner(System.in).nextInt();
        // Получаем второе число
        System.out.println("Please enter you second Number");
        int secondNumber = new Scanner(System.in).nextInt();
        // Считаем частное
        double quotient = (double) firstNumber / secondNumber;
        // Считаем сумму
        int sum = firstNumber + secondNumber;
        // Считаем произведение
        int product = firstNumber * secondNumber;
        // Считаем разность
        int difference=  firstNumber - secondNumber;
        // Выводим на консоль результаты
        System.out.println("quotient is: " + quotient);
        System.out.println("sum is: " + sum);
        System.out.println("product of numbers  is: " + product);
        System.out.println("number difference is: " + difference);


    }
}