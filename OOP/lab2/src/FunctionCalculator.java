import java.util.HashMap;

/**
 * This class can calculate function value
 * it can calculate:
 * sin, cos, tan, atan, log10, log2, sqrt
 */
class FunctionCalculator {
    HashMap<String, FunctionInterface> allFunctions;

    FunctionCalculator() {
        allFunctions = new HashMap<>();
        allFunctions.put("sin", new Sin());
        allFunctions.put("cos", new Cos());
        allFunctions.put("tan", new Tan());
        allFunctions.put("atan", new Atan());
        allFunctions.put("log10", new Log10());
        allFunctions.put("log2", new Log2());
        allFunctions.put("sqrt", new Sqrt());
    }
}

class Sin implements FunctionInterface {
    @Override
    public double calculate(double var1) {
        return Math.sin(var1);
    }
}

class Cos implements FunctionInterface {
    @Override
    public double calculate(double var1) {
        return Math.cos(var1);
    }
}

class Tan implements FunctionInterface {
    @Override
    public double calculate(double var1) {
        return Math.tan(var1);
    }
}

class Atan implements FunctionInterface {
    @Override
    public double calculate(double var1) {
        return Math.atan(var1);
    }
}

class Log10 implements FunctionInterface {
    @Override
    public double calculate(double var1) throws Exception {
        if (var1 < 0) {
            throw new Exception("Negative number cannot be in log");
        }
        return Math.log10(var1);
    }
}

class Log2 implements FunctionInterface {
    @Override
    public double calculate(double var1) throws Exception {
        if (var1 < 0) {
            throw new Exception("Negative number cannot be in log");
        }
        return Math.log(var1) / Math.log(2);
    }
}

class Sqrt implements FunctionInterface {
    @Override
    public double calculate(double var1) throws Exception {
        if (var1 < 0) {
            throw new Exception("Negative number cannot be in sqrt");
        }
        return Math.sqrt(var1);
    }
}