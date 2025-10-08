package com.example.calculator;

import java.util.Scanner;

/**
 * Interactive console application wrapper around {@link Calculator}.
 */
public class CalculatorApp {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select operation: \n" +
                    "1. Add\n" +
                    "2. Subtract\n" +
                    "3. Multiply\n" +
                    "4. Divide\n" +
                    "5. Square Root\n" +
                    "6. Factorial\n" +
                    "7. Natural Logarithm\n" +
                    "8. Power\n" +
                    "9. Exit");
            System.out.print("Enter choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.\n");
                continue;
            }
            try {
                switch (choice) {
                    case 1 -> {
                        float a = readFloat(scanner, "Enter first number: ");
                        float b = readFloat(scanner, "Enter second number: ");
                        System.out.println("Result: " + calc.add(a, b));
                    }
                    case 2 -> {
                        float a = readFloat(scanner, "Enter first number: ");
                        float b = readFloat(scanner, "Enter second number: ");
                        System.out.println("Result: " + calc.subtract(a, b));
                    }
                    case 3 -> {
                        float a = readFloat(scanner, "Enter first number: ");
                        float b = readFloat(scanner, "Enter second number: ");
                        System.out.println("Result: " + calc.multiply(a, b));
                    }
                    case 4 -> {
                        float a = readFloat(scanner, "Enter dividend: ");
                        float b = readFloat(scanner, "Enter divisor: ");
                        System.out.println("Result: " + calc.divide(a, b));
                    }
                    case 5 -> {
                        float a = readFloat(scanner, "Enter number: ");
                        System.out.println("Result: " + calc.squareRoot(a));
                    }
                    case 6 -> {
                        int n = readInt(scanner, "Enter integer: ");
                        System.out.println("Result: " + calc.factorial(n));
                    }
                    case 7 -> {
                        float a = readFloat(scanner, "Enter positive number: ");
                        System.out.println("Result: " + calc.naturalLog(a));
                    }
                    case 8 -> {
                        float base = readFloat(scanner, "Enter base: ");
                        float exp = readFloat(scanner, "Enter exponent: ");
                        System.out.println("Result: " + calc.power(base, exp));
                    }
                    case 9 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.\n");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            System.out.println();
        }
    }

    private static float readFloat(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Float.parseFloat(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Not a valid number. Try again.");
            }
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Not a valid integer. Try again.");
            }
        }
    }
}