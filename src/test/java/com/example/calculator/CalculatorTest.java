package com.example.calculator;

// Test class for Calculator. Calculator is in same package, so no import needed.
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test
    @DisplayName("Addition should add two positive numbers")
    void testAddPositive() {
        assertEquals(7.0f, calculator.add(3.0f, 4.0f));
    }

    @Test
    @DisplayName("Subtraction should subtract second from first")
    void testSubtract() {
        assertEquals(-1.0f, calculator.subtract(3.0f, 4.0f));
    }

    @Test
    void testMultiply() {
        assertEquals(12.0f, calculator.multiply(3.0f, 4.0f));
    }

    @Test
    void testDivideNormal() {
        assertEquals(2.0f, calculator.divide(8.0f, 4.0f));
    }

    @Test
    void testDivideByZero() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> calculator.divide(5.0f, 0));
        assertTrue(ex.getMessage().contains("Division by zero"));
    }

    @Test
    void testSquareRoot() {
        assertEquals(5.0f, calculator.squareRoot(25.0f), 0.0001f);
    }

    @Test
    void testSquareRootNegative() {
        assertThrows(IllegalArgumentException.class, () -> calculator.squareRoot(-4.0f));
    }

    @Test
    void testFactorialBase() {
        assertEquals(1, calculator.factorial(0));
    }

    @Test
    void testFactorialTypical() {
        assertEquals(120, calculator.factorial(5));
    }

    @Test
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calculator.factorial(-2));
    }

    @Test
    void testNaturalLog() {
        float result = calculator.naturalLog((float) Math.E);
        assertEquals(1.0f, result, 0.0001f);
    }

    @Test
    void testNaturalLogNonPositive() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> calculator.naturalLog(0)),
                () -> assertThrows(IllegalArgumentException.class, () -> calculator.naturalLog(-1))
        );
    }

    @Test
    void testPower() {
        assertEquals(8.0f, calculator.power(2.0f, 3.0f));
    }
}
