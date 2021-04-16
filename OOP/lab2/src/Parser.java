import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Divides line with equation to note in RPN
 */
class Parser extends Constants {

    /**
     * Checks line for the correct input
     * - is line empty
     * - is line has incorrect number of brackets
     * - is line has characters that program cannot handle
     *
     * @param arg - line
     * @throws Exception - Line empty
     */
    void checkLine(String[] arg) throws Exception {
        if (arg.length == 0) throw new Exception("The line is empty");

        for (int i = 0; i < arg[0].length(); i++) {
            if (!(matches(String.valueOf(arg[0].charAt(i)), LETTERS)
                    || matches(String.valueOf(arg[0].charAt(i)), NUMBER)
                    || matches(String.valueOf(arg[0].charAt(i)), ALL_ARITHMETICAL_OPERATIONS))) {
                throw new Exception("There are characters that program can't handle. Please, check input line.");
            }
        }

        int openBracket = 0, closeBracket = 0;
        for (int i = 0; i < arg[0].length(); i++) {
            if (arg[0].toCharArray()[i] == '(') openBracket++;
            if (arg[0].toCharArray()[i] == ')') closeBracket++;
        }
        if (openBracket != closeBracket) throw new Exception("Wrong parentheses");
    }

    /**
     * Fills HashMap with arrays and numeric numbers
     *
     * @param args - input String array
     */
    HashMap<String, Double> fillInVariablesInHashMap(String[] args) throws Exception {
        HashMap<String, Double> variables = new HashMap<>();
        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                String[] letter = args[i].split("=");
                if (!matches(letter[0], LETTERS)) {
                    throw new Exception("Incorrect VARIABLES");
                }
                variables.put(letter[0], Double.valueOf(letter[1]));
            }
        }
        return variables;
    }

    /**
     * Parses input line into RPN
     */
    String parseLine(String line) {
        Stack<String> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(line, "/*^()-+", true);
        StringBuilder parsedLine = new StringBuilder();
        String currentToken = null, prevToken;

        while (tokenizer.hasMoreTokens()) {
            prevToken = currentToken;
            currentToken = tokenizer.nextToken();

            if ((matches(currentToken, NUMBER) || matches(currentToken, LETTERS)) && !FUNCTIONS.contains(currentToken)) {
                parsedLine.append(currentToken).append(" ");
                continue;
            }

            if (matches(currentToken, ALL_ARITHMETICAL_OPERATIONS) || FUNCTIONS.contains(currentToken)) {

                if (!stack.isEmpty() && FUNCTIONS.contains(currentToken)) {
                    stack.push(currentToken);
                    continue;
                }

                if ((prevToken == null && currentToken.equals("-"))
                        || (prevToken != null && prevToken.equals("(") && currentToken.equals("-"))) {
                    parsedLine.append("0").append(" ");
                    stack.push("-");
                    continue;
                }

                if (currentToken.equals("(")) {
                    stack.push(currentToken);
                    continue;
                }

                if (currentToken.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        parsedLine.append(stack.pop()).append(" ");
                    }
                    stack.pop();
                    if (!stack.isEmpty() && FUNCTIONS.contains(stack.peek())) {
                        parsedLine.append(stack.pop()).append(" ");
                    }

                    continue;
                }

                if (!stack.isEmpty() && stack.peek().equals("^") && currentToken.equals("^")) {
                    stack.push(currentToken);
                    continue;
                }

                while (!stack.isEmpty() && (getPriority(stack.peek()) > getPriority(currentToken)
                        || getPriority(stack.peek()) == getPriority(currentToken))) {
                    parsedLine.append(stack.pop()).append(" ");
                }
                stack.push(currentToken);
            }
        }

        while (!stack.isEmpty()) {
            parsedLine.append(stack.peek());
            if (!stack.isEmpty()) parsedLine.append(" ");
            stack.pop();
        }

        return parsedLine.toString();
    }

    /**
     * Determine what prioritise in arithmetical operation
     *
     * @param operation - arithmetical operation
     * @return - number of operation priority
     */
    private int getPriority(String operation) {
        switch (operation) {
            case "-":
            case "+":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return 0;
        }
    }
}
