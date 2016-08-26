import java.util.HashMap;

/**
 * Created by Denis on 23.06.2016.
 * Get from user console input math expression and variable,
 * calculate it and print on console double result
 */
public class Calculator {
    public static void main(String[] args) {
        run();
    }

    private static void run() {
        String expression;
        MyHashMap<String, Double> variables;
        double result;
        try {
            expression = ConsoleInput.getUserExpression();
            variables = ConsoleInput.getUserVariables();
            CalculateExpression calc = new CalculateExpression(expression, variables);
            result = calc.calculate();
            System.out.println("Result of " + expression + " is " + result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
