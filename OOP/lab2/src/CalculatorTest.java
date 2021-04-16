import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculatorTest {
    //Calculator

    Calculator calculator = new Calculator(new String[]{""});
    Parser parser = new Parser();

    @Test
    @DisplayName("Calculating expressions")
    void calculatedExpressionsAssertion() {
        Assertions.assertEquals(5.78960446186581E77, calculator.calculate(new String[]{"5*(2^2^(1+1)^3)"}));
        Assertions.assertEquals(2049.7473442079713, calculator.calculate(new String[]{"(log2(45-12))^3*(2^5-2^(2^2))-(5-(6-5*(21-20)))"}));
        Assertions.assertEquals(2327.6, calculator.calculate(new String[]{"((-((789-45)/(89-88)^2)*0.1+1)+7^4)"}));
        Assertions.assertEquals(2327.6, calculator.calculate(new String[]{"((-((a-45)/(b-88)^2)*c+1)+7^4)", "a=789", "b=89", "c=0.1"}));
    }

    @Test
    @DisplayName("Is log2 correct")
    void log2CalculationAssertion() {
        Assertions.assertEquals(Math.log(256) / Math.log(2), calculator.calculate(new String[]{"log2(256)"}));
        Assertions.assertEquals(Math.log(0) / Math.log(2), calculator.calculate(new String[]{"log2(0)"}));
        Assertions.assertEquals(Math.log(78529) / Math.log(2), calculator.calculate(new String[]{"log2(78529)"}));
        Assertions.assertEquals(Math.log(1) / Math.log(2), calculator.calculate(new String[]{"log2(1)"}));
        try {
            calculator.calculate(new String[]{"log2(-32)"});
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Negative number cannot be in log"));
        }
    }

    @Test
    @DisplayName("Is log10 work correct")
    void log10CalculationAssertion() {
        Assertions.assertEquals(Math.log10(100), calculator.calculate(new String[]{"log10(100)"}));
        Assertions.assertEquals(Math.log10(0), calculator.calculate(new String[]{"log10(0)"}));
        Assertions.assertEquals(Math.log10(456456), calculator.calculate(new String[]{"log10(456456)"}));
        Assertions.assertEquals(Math.log10(1), calculator.calculate(new String[]{"log10(1)"}));
        try {
            calculator.calculate(new String[]{"log10(-5)"});
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Negative number cannot be in log"));
        }
    }


    @Test
    @DisplayName("Divide by zero")
    void divideByZeroAssertion() {
        try {
            calculator.calculate(new String[]{"((-((789-45)/(89-89)^2)*0+1)+7^4)"});
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Can't divide by zero"));
        }
        Assertions.assertEquals(0, calculator.calculate(new String[]{"((-((789-45)/(89-89)^2)*0+1)+7^4)"}));
    }

    //Parser
    @Test
    @DisplayName("Correct RPN parser")
    void RPNAssertion() {
        calculator.calculate(new String[]{"(6+10-4)/(1+1*2)+1"});
        Assertions.assertEquals("6 10 + 4 - 1 1 2 * + / 1 + ", calculator.getRPNNote());

        calculator.calculate(new String[]{"1+2*3*4+(5-6)*7*8+9"});
        Assertions.assertEquals("1 2 3 * 4 * + 5 6 - 7 * 8 * + 9 + ", calculator.getRPNNote());

        calculator.calculate(new String[]{"2^3^4^5^6^7^8"});
        Assertions.assertEquals("2 3 4 5 6 7 8 ^ ^ ^ ^ ^ ^ ", calculator.getRPNNote());

        calculator.calculate(new String[]{"(log2(45-12))^3*(2^5-2^(log10(100000)))-(tan(1)-(cos(0)-5*(sqrt(16)-sqrt(1024))))"});
        Assertions.assertEquals("45 12 - log2 3 ^ 2 5 ^ 2 100000 log10 ^ - * 1 tan 0 cos 5 16 sqrt 1024 sqrt - * - - - ", calculator.getRPNNote());

    }

    @Test
    @DisplayName("Parser checkLine() test")
    void checkLineAssertion() {
        try {
            parser.checkLine(new String[]{"(log2(45-12))^3*(2^5-2^(2^2))-(5-(6-5*(21-20)))"});
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("Wrong parentheses"));
        }

        try {
            parser.checkLine(new String[]{"(log2(45-12))&3*(2&5-2&(2&2))-(5-(6-5*(21-20)))"});
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("There are characters that program can't handle. Please, check input line."));
        }

        try {
            parser.checkLine(new String[]{});
        } catch (Exception e) {
            Assertions.assertTrue(e.getMessage().contains("The line is empty"));
        }

    }
}
