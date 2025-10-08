package com.example.calculator;

public class Calculator {
    public Calculator() {}

    public float add(float a, float b) { return a + b; }
    public float subtract(float a, float b) { return a - b; }
    public float multiply(float a, float b) { return a * b; }
    public float divide(float a, float b) {
        if (b == 0) throw new IllegalArgumentException("Division by zero is not allowed.");
        return a / b;
    }
    public float squareRoot(float a) {
        if (a < 0) throw new IllegalArgumentException("Square root of negative number is not allowed.");
        return (float) Math.sqrt(a);
    }
    public int factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Factorial of negative number is not allowed.");
        int result = 1;
        for (int i = 1; i <= n; i++) result *= i;
        return result;
    }
    public float naturalLog(float a) {
        if (a <= 0) throw new IllegalArgumentException("Natural logarithm of non-positive number is not allowed.");
        return (float) Math.log(a);
    }
    public float power(float a, float b) { return (float) Math.pow(a, b); }
}
