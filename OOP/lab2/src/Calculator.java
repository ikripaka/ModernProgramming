import java.util.HashMap;
import java.util.Stack;

/**
 * -------- CALCULATOR --------
 * This class calculates expressions
 * which can contain these symbols:
 * ^, *, /, +, -, (, )
 * and these functions:
 * sin, cos, tan, atan, log10, log2, sqrt
 * <p>
 * It bases on the ReversePolishNotation
 * <p>
 * Calculates expression
 * - checks given line
 * - converts line to Reverse Polish Notation
 * - calculates expression in RPN
 * Also this class saves all info about expression such as:
 * - Converted expression to RPN
 * - calculation result
 * - main line what contains main info about expression
 */

class Calculator extends Constants {
    private double result = 0;

    private String RPNNote;
    private String[] args;
    private Parser parser;
    private final FunctionCalculator functionCalculator;

    /**
     * Contains line that divided by " "
     */
    private String[] dividedLine;
    /**
     * Contains variables that user wrote
     */
    private HashMap<String, Double> allVariables;

    Calculator(String[] args) {
        this.args = args;
        parser = new Parser();
        functionCalculator = new FunctionCalculator();
    }

    /**
     * @param args - arguments for calculating expression
     *             original args that was given in the creation of Calculator are changing to new one
     * @return - result of calculation
     */
    double calculate(String[] args) {
        try {
            this.args = args;

            parser.checkLine(args);

            allVariables = parser.fillInVariablesInHashMap(args);

            RPNNote = parser.parseLine(args[0]);

            dividedLine = RPNNote.split(" ");

            calculateRPN();

        } catch (Exception e) {
            System.err.println("Something went wrong");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Calculates expression that was given in constructor creation.
     * Variable "args" can be changes with the invoking
     * calculate(String[] args)
     *
     * @return - result
     */
    double calculate() {
        return calculate(args);
    }

    /**
     * Calculates RPN expression from left side to right one
     */
    void calculateRPN() throws Exception {
        Stack<Double> stack = new Stack<>();
            for (int i = 0; i < dividedLine.length; i++) {
                if (!matches(dividedLine[i], ALL_ARITHMETICAL_OPERATIONS) && !FUNCTIONS.contains(dividedLine[i])) {
                    if (!matches(dividedLine[i], NUMBER)) {
                        if (allVariables.size() == 0) {
                            System.err.println("You didn't write variable(s) value(s)");
                            System.exit(0);
                        }
                        stack.push(allVariables.get(dividedLine[i]));
                    } else {
                        stack.push(Double.parseDouble(dividedLine[i]));
                    }
                } else {
                    if (matches(dividedLine[i], ALL_ARITHMETICAL_OPERATIONS)) {
                        stack.push(performArithmeticAction(stack.pop(), stack.pop(), dividedLine[i]));
                    } else {
                        stack.push(functionCalculator.allFunctions.get(dividedLine[i]).calculate(stack.pop()));
                    }
                }
            }
            result = stack.pop();


    }

    /**
     * Calculates result according to arithmetical operation
     *
     * @param num2      - second number
     * @param num1      - first number
     * @param operation - arithmetical operation
     * @return - result after operation on this numbers
     * @throws Exception
     */
    private double performArithmeticAction(double num2, double num1, String operation) throws Exception {
        if (operation.equals("/") && num2 == 0) throw new Exception("Can't divide by zero");

        switch (operation) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                return num1 / num2;
            case "^":
                return Math.pow(num1, num2);
            default:
                return 0;
        }
    }

    /**
     * Gets Args that was given in constructor or in calculate(String[] args)
     * @return - arguments from what expression will be calculated
     */
    String[] getArgs() {
        return args;
    }

    /**
     * Gets parsed input expression in ReversePolishNotation
     * @return - RPN note
     */
    String getRPNNote() {
        return RPNNote;
    }

    /**
     * Gets calculation result
     * @return - result
     */
    double getResult() {
        return result;
    }

}
