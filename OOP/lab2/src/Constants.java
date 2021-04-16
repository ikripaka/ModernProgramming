import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class consists of the constants which is using in the classes
 */

class Constants {
    static final Pattern NUMBER = Pattern.compile("[\\d|\\.]+");
    static final Pattern LETTERS = Pattern.compile("[a-z|A-Z]+");

    static final Pattern ALL_ARITHMETICAL_OPERATIONS = Pattern.compile("[/^*+\\-()]");
    static final ArrayList<String> FUNCTIONS = new ArrayList<>();

    Constants() {
        String[] allFunctions = {"sin", "cos", "tan", "atan", "log10", "log2", "sqrt"};
        FUNCTIONS.addAll(Arrays.asList(allFunctions));
    }

    /**
     * Checks if the symbol - operator
     *
     * @param symbols - one symbol in String
     * @return - true/false  if symbol matches
     */
    static boolean matches(String symbols, Pattern pattern) {
        Matcher match = pattern.matcher(String.valueOf(symbols));
        return match.matches();
    }
}
