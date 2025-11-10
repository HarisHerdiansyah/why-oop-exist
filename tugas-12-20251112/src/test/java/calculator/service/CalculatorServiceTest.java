package calculator.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CalculatorService unit tests")
public class CalculatorServiceTest {
    @Test
    @DisplayName("Addition operation")
    public void additionTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("2 + 3");
        assertEquals(5.0, result, 0.0001);
    }

    @Test
    @DisplayName("Subtraction operation")
    public void subtractionTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("10 - 3");
        assertEquals(7.0, result, 0.0001);
    }

    @Test
    @DisplayName("Multiplication operation")
    public void multiplicationTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("5 * 9");
        assertEquals(45.0, result, 0.0001);
    }

    @Test
    @DisplayName("Division operation")
    public void divisionTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("9 / 3");
        assertEquals(3.0, result, 0.0001);
    }

    @Test
    @DisplayName("PI constant")
    public void piConstantTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("pi");
        assertEquals(3.141592653589793, result, 0.0001);
    }

    @Test
    @DisplayName("Modulo operation")
    public void moduloTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("mod(10,3)");
        assertEquals(1.0, result, 0.0001);
    }

    @Test
    @DisplayName("Exponent operation")
    public void exponentTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("2 ^ 3");
        assertEquals(8.0, result, 0.0001);
    }

    @Test
    @DisplayName("Square root operation")
    public void squareRootTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("sqrt(16)");
        assertEquals(4.0, result, 0.0001);
    }

    @Test
    @DisplayName("Cubic root operation")
    public void cubicRootTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("root(3, 27)");
        assertEquals(3.0, result, 0.0001);
    }

    @Test
    @DisplayName("Natural exponential operation")
    public void naturalExponentialTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("exp(1)");
        assertEquals(2.718281828459045, result, 0.0001);
    }

    @Test
    @DisplayName("Custom base logarithm operation")
    public void customBaseLogarithmTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("log(2, 8)");
        assertEquals(3.0, result, 0.0001);
    }

    @Test
    @DisplayName("Base 10 logarithm operation")
    public void baseTenLogarithmTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("log10(1)");
        assertEquals(0, result, 0.0001);
    }

    @Test
    @DisplayName("Natural logarithm operation")
    public void naturalLogarithmTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("ln(1)");
        assertEquals(0, result, 0.0001);
    }

    @Test
    @DisplayName("Factorial operation")
    public void factorialTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("5!");
        assertEquals(120.0, result, 0.0001);
    }

    @Test
    @DisplayName("Permutation operation")
    public void permutationTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("nPk(5, 2)");
        assertEquals(20.0, result, 0.0001);
    }

    @Test
    @DisplayName("Combination operation")
    public void combinationTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("nCk(5, 2)");
        assertEquals(10.0, result, 0.0001);
    }

    @Test
    @DisplayName("Sine operation")
    public void sineTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("sin(30 * [deg])");
        assertEquals(0.5, result, 0.0001);
    }

    @Test
    @DisplayName("Cosine operation")
    public void cosineTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("cos(60 * [deg])");
        assertEquals(0.5, result, 0.0001);
    }

    @Test
    @DisplayName("Tangent operation")
    public void tangentTest() {
        CalculatorService calculatorService = new CalculatorService();
        double result = calculatorService.calculateExpression("tan(45 * [deg])");
        assertEquals(1.0, result, 0.0001);
    }
}
